package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.Primitive;
import com.cwi.managed_data.klass_system.Schema;
import com.cwi.managed_data.klass_system.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

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

                    String methodName = method.getName();

                    if (methodName.equals("types")) {

                        Set<Type> types = new HashSet<>();

                        for (Method declaredMethods : _schemaFactoryClass.getMethods()) {
                            Class typeClass = declaredMethods.getReturnType();
                            Type typeType = TypeFactory.make(typeClass);

                            types.add(typeType);
                        }

                        return types;
                    }

                    return null;
                }
        );
    }

}