package com.cwi.managed_data.klass_system.factories.proxied;

import com.cwi.managed_data.klass_system.Primitive;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PrimitiveFactory {

    public static Primitive make(Class _schemaClass) {
        return (Primitive) Proxy.newProxyInstance(
                Primitive.class.getClassLoader(),
                new Class<?>[]{Primitive.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {
                    return null;
                }
        );
    }
}