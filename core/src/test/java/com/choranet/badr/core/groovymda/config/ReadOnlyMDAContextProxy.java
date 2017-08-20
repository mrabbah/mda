/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */

package com.choranet.badr.core.groovymda.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;


/**
 *  <p>Wraps a context and throws an exception on <code>setXXX</code> 
 *  and <code>Map</code> alterations.  Provides basic protection against
 *  a unit test altering AbstractConfigTest's shared <code>context</code> field.
 *  </p>
 *  <p>Note: this provides only the most basic protection; alterations to 
 *  nested elements within a context will not be caught.</p>
 *   
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public class ReadOnlyMDAContextProxy implements java.lang.reflect.InvocationHandler {

    private MDAContext context;

    private static Set modifyingMethods = new HashSet();
    static {
        modifyingMethods.add("put");
        modifyingMethods.add("putAll");
        modifyingMethods.add("clear");
        modifyingMethods.add("remove");
    }

    public static MDAContext newInstance(MDAContext context) {
        return (MDAContext) java.lang.reflect.Proxy.newProxyInstance(
                context.getClass().getClassLoader(),
                new Class[] { MDAContext.class },
                new ReadOnlyMDAContextProxy(context)
        );
    }

    private ReadOnlyMDAContextProxy(MDAContext context) {
        this.context = context;
    }

    /**
     * Catches any <code>set</code> or <code>Map</code> alterations on the
     * context's root values.
     */
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        boolean beginsWithSet = m.getName().startsWith("set");
        
        if (beginsWithSet || modifyingMethods.contains(m.getName())) {
            throw new UnsupportedOperationException(
                    "This MDAContext is unmodifiable, cannot perform " + m.getName()
            );        
        }
                
        Object result;
        try {
            result = m.invoke(context, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException(
                    "unexpected invocation exception: " + e.getMessage()
            );
        } 
        return result;
    }
}