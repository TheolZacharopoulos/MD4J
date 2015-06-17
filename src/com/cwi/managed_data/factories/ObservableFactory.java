package com.cwi.managed_data.factories;

import com.cwi.managed_data.managed_objects.ObservableManagedObject;
import com.cwi.managed_data.roles.Observable;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ObservableFactory extends Factory {

    public static Object make(Class _schemaFactoryClass) {
        return Proxy.newProxyInstance(
                _schemaFactoryClass.getClassLoader(),
                new Class<?>[]{_schemaFactoryClass},
                new ObservableFactory());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // Get the type which the factory returns (the schema)
        Class schema = method.getReturnType();

        // Create a new proxied object of the returned type,
        // with invocation handler the given dataManager
        return Proxy.newProxyInstance(
                ObservableManagedObject.class.getClassLoader(),
                new Class<?>[]{schema, Observable.class},
                new ObservableManagedObject(schema));
    }
}
