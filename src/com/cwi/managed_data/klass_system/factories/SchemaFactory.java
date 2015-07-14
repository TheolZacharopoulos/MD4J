package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Schema;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Schema.
 */
public class SchemaFactory {

    public static Schema make(Class _schemaFactoryClass) {
        return (Schema) Proxy.newProxyInstance(
                Schema.class.getClassLoader(),
                new Class<?>[]{Schema.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    //TODO: ...
                    for (Method declaredMethods : _schemaFactoryClass.getMethods()) {

                    }

                    return null;
                }
        );
    }

}