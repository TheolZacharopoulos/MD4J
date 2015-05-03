package com.cwi.examples;

import java.lang.reflect.*;

/**
 * Subclasses of Delegator can override invokeNotDelegated to implement
 * the behavior of proxy method invocations not to be directly delegated
 * to other objects, and they can override proxyHashCode, proxyEquals,
 * and proxyToString to override the default behavior of the methods the
 * proxy inherits from java.lang.Object.
 *
 */
public class Delegator implements InvocationHandler {

    // preloaded Method objects for the methods in java.lang.Object
    private static Method hashCodeMethod;
    private static Method equalsMethod;
    private static Method toStringMethod;
    static {
        try {
            hashCodeMethod = Object.class.getMethod("hashCode", null);
            equalsMethod =
                    Object.class.getMethod("equals", new Class[] { Object.class });
            toStringMethod = Object.class.getMethod("toString", null);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    private Class[] interfaces;
    private Object[] delegates;

    public Delegator(Class[] interfaces, Object[] delegates) {
        this.interfaces = (Class[]) interfaces.clone();
        this.delegates = (Object[]) delegates.clone();
    }

    /**
     * @param proxy the proxy instance, which is of type Object.
     * @param m the interface method invoked on the proxy instance.
     * @param args an array of objects containing the values of the arguments passed
     *             in the method invocation on the proxy instance.
     *             The implementation of the invoke method is free to modify the contents of this array.
     * @return  the return value of the method invocation on the proxy instance.
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable
    {
        Class declaringClass = m.getDeclaringClass();

        if (declaringClass == Object.class) {
            if (m.equals(hashCodeMethod)) {
                return proxyHashCode(proxy);
            } else if (m.equals(equalsMethod)) {
                return proxyEquals(proxy, args[0]);
            } else if (m.equals(toStringMethod)) {
                return proxyToString(proxy);
            } else {
                throw new InternalError(
                        "unexpected Object method dispatched: " + m);
            }
        } else {
            for (int i = 0; i < interfaces.length; i++) {
                if (declaringClass.isAssignableFrom(interfaces[i])) {
                    try {
                        return m.invoke(delegates[i], args);
                    } catch (InvocationTargetException e) {
                        throw e.getTargetException();
                    }
                }
            }

            return invokeNotDelegated(proxy, m, args);
        }
    }

    protected Object invokeNotDelegated(Object proxy, Method m,
                                        Object[] args)
            throws Throwable
    {
        throw new InternalError("unexpected method dispatched: " + m);
    }

    protected Integer proxyHashCode(Object proxy) {
        return new Integer(System.identityHashCode(proxy));
    }

    protected Boolean proxyEquals(Object proxy, Object other) {
        return (proxy == other ? Boolean.TRUE : Boolean.FALSE);
    }

    protected String proxyToString(Object proxy) {
        return proxy.getClass().getName() + '@' +
                Integer.toHexString(proxy.hashCode());
    }
}