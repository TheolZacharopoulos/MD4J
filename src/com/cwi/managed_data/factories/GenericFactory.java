package com.cwi.managed_data.factories;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericFactory implements InvocationHandler {

    public static Object newFactory(Class _factory) {
        return Proxy.newProxyInstance(
                _factory.getClassLoader(),
                new Class<?>[]{_factory},
                new GenericFactory(_factory));
    }

    protected GenericFactory(Class _factory) {

    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return null;
    }
}
