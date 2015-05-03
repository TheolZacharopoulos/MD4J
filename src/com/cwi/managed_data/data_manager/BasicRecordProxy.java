package com.cwi.managed_data.data_manager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class BasicRecordProxy implements InvocationHandler {

    private Map<String, Class> types = new HashMap<String, Class>();
    private Map<String, Object> values = new HashMap<String, Object>();

    public static Object newInstance(Class _schema) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[]{_schema},
                new BasicRecordProxy(_schema));
    }

    private BasicRecordProxy(Class _schema) {

        // Initialize types.
        for (Method schemaType : _schema.getMethods()) {
            types.put(schemaType.getName(), schemaType.getReturnType());
        }

        // Initialize values.
        for (String name : types.keySet()) {

            // TODO: Omit this because Java does the checking already (interface)???
            if (types.get(name) == Integer.class) {
                values.put(name, 0);
            } else if (types.get(name) == String.class) {
                values.put(name, "");
            } else if (types.get(name) == Double.class) {
                values.put(name, 0.0);
            } else if (types.get(name) == Object.class) {
                values.put(name, new Object());
            }
        }
    }

    private void set(String _name, Object _value) {
        values.put(_name, _value);
    }

    private Object get(String _name) {
        return values.get(_name);
    }

    /**
     * Handle all fields.
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        Object [] proxyMethodVarArgs = (Object []) args[0];
        String methodName = method.getName();
        boolean isAssignment = false;

        // TODO: Think of it, find a better strategy to decide if it is assignment.
        // If there is an argument then is considered as assignment.
        if (proxyMethodVarArgs.length > 0) {
            isAssignment = true;
        }

        // If is assignment
        if (isAssignment) {

            // TODO: Omit this because Java does the checking already (interface)???
            // If there is no such type.
            if (!types.containsKey(methodName)) {
                throw new NoSuchFieldError();
            }

            // TODO: Omit this because Java does the checking already (interface)???
            // If the argument is of the right type.
            if (proxyMethodVarArgs[0].getClass() != values.get(methodName).getClass()) {
                throw new IllegalArgumentException();
            }

            set(methodName, proxyMethodVarArgs[0]);

        } else { // If is not assignment, return the value.
            return get(methodName);
        }

        return null;
    }
}