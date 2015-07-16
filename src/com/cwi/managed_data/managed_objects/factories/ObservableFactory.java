package com.cwi.managed_data.managed_objects.factories;

import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.factories.KlassFactory;
import com.cwi.managed_data.managed_objects.ObservableManagedObject;
import com.cwi.managed_data.roles.Observable;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class ObservableFactory extends GenericFactory {

    public static <T> T newFactory(Class _schemaFactoryClass) {
        return (T) Proxy.newProxyInstance(
                    _schemaFactoryClass.getClassLoader(),
                    new Class<?>[]{_schemaFactoryClass},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    // Get the type which the factory returns (the Schema Class)
                    Class schemaClass = method.getReturnType();

                    // Create the schema Klass based on the schema.
                    Klass schemaKlass = KlassFactory.make(schemaClass);

                    // Create a new proxied object of the returned type,
                    // with invocation handler the given dataManager
                    return Proxy.newProxyInstance(
                        ObservableManagedObject.class.getClassLoader(),
                        new Class<?>[]{schemaClass, Observable.class},
                        new ObservableManagedObject(schemaKlass)
                    );
                });
    }
}
