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
package com.fharms.marshalling.resolvers;

import org.hibernate.proxy.HibernateProxy;
import org.jboss.marshalling.ObjectResolver;

import com.fharms.marshalling.utils.HibernateDetachUtil;

/**
 * Detach Hibernate object from it's session when it's serialized 
 * with JBossMarshalling. This run as a PreResolver to make sure the
 * object is converted from a {@link HibernateProxy} before the serialization
 * actually happens, otherwise we will just end up serialize the proxy object 
 * 
 * @author Flemming Harms (flemming.harms at gmail.com)
 *
 */
public class HibernateDetachResolver implements ObjectResolver {

    private final HibernateDetachUtil hibernateDetachUtil;

    public HibernateDetachResolver() {
        hibernateDetachUtil = new HibernateDetachUtil();
    }
    
    @Override
    public Object readResolve(Object replacement) {
        return replacement;
    }

    @Override
    public Object writeReplace(Object original) {
        Object cleanObject = HibernateDetachUtil.clean(original);
       return hibernateDetachUtil.cleanCastCollection(cleanObject,Object.class);
    }

}
