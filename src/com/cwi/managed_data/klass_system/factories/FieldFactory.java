package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Klass;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Field.
 */
public class FieldFactory {

    public static Field make(Klass klass, String _fieldName) {
        return (Field) Proxy.newProxyInstance(
                Field.class.getClassLoader(),
                new Class<?>[]{Field.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {
                    System.out.println("(FieldFactory) : " + method.getName());
                    return null;
                }
        );
    }

}