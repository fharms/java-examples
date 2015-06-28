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
package com.fharms.marshalling.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.internal.PersistentSet;
import org.hibernate.collection.internal.PersistentSortedMap;
import org.hibernate.collection.internal.PersistentSortedSet;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.pojo.javassist.SerializableProxy;

/**
 * Utility to detach any Hibernate Entity from the session and cleanup proxy object. 
 *
 * @author Flemming.Harms at gmail.com
 *
 */
public class HibernateDetachUtil {

    private Set<Class<?>> hibernateCollectionClasses;

    public HibernateDetachUtil() {
        hibernateCollectionClasses(PersistentList.class, PersistentSet.class, PersistentMap.class, PersistentSortedMap.class,
                PersistentBag.class, PersistentSortedSet.class);
    }

    /**
     * Clean the object for any Hibernate Collections types and Proxy information
     * @param <T>
     * 
     * @param source - The object it should clean
     * @return the clean object
     */
    public <T extends Object> T cleanCastCollection(final Object source, Class<T> type) {
        Class<?> sourceClass = source.getClass();
        if (isProxyCollectionClass(sourceClass)) {
            return type.cast(cleanInitializedObject(source, sourceClass));
        }
        return type.cast(source);
    }

    @SuppressWarnings("unchecked")
    public static <If, Impl extends If> Impl clean(final If object) {
        if (isProxy(object)) {
            final HibernateProxy proxy = (HibernateProxy) object;
            final Impl impl = (Impl) proxy.getHibernateLazyInitializer().getImplementation();
            Hibernate.initialize(impl);
            return impl;
        } else if (isSerializableProxy(object)) {
            Hibernate.initialize(object);
            return (Impl) object;
        }
        return (Impl) object;
    }
    
    
    private Object cleanInitializedObject(final Object source, final Class<?> sourceClass) {
        if (sourceClass == null || Object.class.equals(sourceClass)) {
            return null;
        }

        Hibernate.initialize(source);

        Field[] fields = sourceClass.getDeclaredFields();

        try {
            for (Field field : fields) {
                String name = field.getName();
                if (name.equals("map")) { //$NON-NLS-1$
                    field.setAccessible(true);
                    Object object = field.get(source);
                    return Collections.emptyMap().getClass().isInstance(object) || object == null ? new HashMap<Object, Object>() : object;
                } else if (name.equals("set")) { //$NON-NLS-1$
                    field.setAccessible(true);
                    Object object = field.get(source);
                    return Collections.emptySet().getClass().isInstance(object) || object == null ? new HashSet<Object>() : object;
                } else if (name.equals("list")) { //$NON-NLS-1$
                    field.setAccessible(true);
                    Object object = field.get(source);
                    return Collections.emptyList().getClass().isInstance(object) || object == null ? new ArrayList<Object>() : object;
                } else if (name.equals("bag")) { //$NON-NLS-1$
                    field.setAccessible(true);
                    Object object = field.get(source);
                    return Collections.emptyList().getClass().isInstance(object) || object == null ? new ArrayList<Object>() : object;
                }
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return cleanInitializedObject(source, sourceClass.getSuperclass());
    }

    /**
     * @param type - the class it should test for hibernate collections classes
     * @return true if it's a hibernate collection type
     */
    private boolean isProxyCollectionClass(final Class<?> type) {
        final boolean result = hibernateCollectionClasses.contains(type);
        return result;
    }

    private void hibernateCollectionClasses(final Class<?>... proxyCollectionClasses) {
        this.hibernateCollectionClasses = new TreeSet<Class<?>>(new ClassNameComparator());
        for (Class<?> clazz : proxyCollectionClasses) {
            this.hibernateCollectionClasses.add(clazz);
        }
    }
    
    private static boolean isProxy(Object o) {
        try {
            return (o instanceof HibernateProxy);
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    private static boolean isSerializableProxy(Object o) {
        try {
            return (o instanceof SerializableProxy);
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    private static class ClassNameComparator implements Comparator<Class<?>> {

        @Override
        public int compare(Class<?> o1, Class<?> o2) {
            final String cn1 = o1.getName();
            final String cn2 = o2.getName();
            return cn1.compareTo(cn2);
        }

    }
}
