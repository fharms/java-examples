/**
 * The MIT License
 * Copyright (c) ${project.inceptionYear} Flemming Harms
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.fharms.marshalling.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import org.jboss.marshalling.ChainingObjectResolver;
import org.jboss.marshalling.ExceptionListener;
import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.SimpleClassResolver;
import org.jboss.marshalling.Unmarshaller;

import com.fharms.marshalling.resolvers.HibernateDetachResolver;

/**
 * Marshalling service for serializing and deserializing objects
 * 
 * The service also setup Object resolvers to intercept the objects
 * when they are processing by JBoss Marshalling.
 * 
 *  @author Flemming Harms (flemming.harms at gmail.com)
 *
 */
public class MarshallingService {

    private final MarshallerFactory marshallerFactory;
    private MarshallingConfiguration configuration;

    public MarshallingService() {
        this.marshallerFactory = Marshalling.getProvidedMarshallerFactory("river"); //$NON-NLS-1$
        if (this.marshallerFactory == null) {
            throw new IllegalStateException("Unable to create marshaller factory"); //$NON-NLS-1$
        }
        configuration = new MarshallingConfiguration();
        configuration.setVersion(3);
        configuration.setClassCount(10);
        configuration.setBufferSize(8096);
        configuration.setInstanceCount(100);
        configuration.setExceptionListener(new MarshallingException());
        configuration.setClassResolver(new SimpleClassResolver(getClass().getClassLoader()));
    }

    private Marshaller createMarshaller() throws IOException {
        configuration
                .setObjectPreResolver(new ChainingObjectResolver(Collections.singletonList(new HibernateDetachResolver())));
        return marshallerFactory.createMarshaller(configuration);
    }

    private Unmarshaller createUnmarshaller() throws IOException {
        return marshallerFactory.createUnmarshaller(configuration);
    }

    public void serialize(Object object, OutputStream outputStream) throws IOException {
        Marshaller marshaller = createMarshaller();
        marshaller.start(Marshalling.createByteOutput(outputStream));
        try {
            marshaller.writeObject(object);
        } finally {
            marshaller.finish();
        }
    }

    public <T extends Object> T deserialize(InputStream inputStream, Class<T> type) throws Exception {
        Unmarshaller unmarshaller = createUnmarshaller();
        unmarshaller.start(Marshalling.createByteInput(inputStream));
        try {
            return type.cast(unmarshaller.readObject());
        } finally {
            unmarshaller.finish();
        }
    }
    private class MarshallingException implements ExceptionListener {

        @Override
        public void handleMarshallingException(Throwable problem, Object subject) {
            throw new RuntimeException(String.format("Unable to marshall object %s", subject.getClass().getName()), problem); //$NON-NLS-1$
        }

        @Override
        public void handleUnmarshallingException(Throwable problem, Class<?> subjectClass) {
            throw new RuntimeException(String.format("Unable to unmarshall object %s", subjectClass), problem); //$NON-NLS-1$
        }

        @Override
        public void handleUnmarshallingException(Throwable problem) {
            throw new RuntimeException("Unable to unmarshall object", problem); //$NON-NLS-1$
        }

    }
}
