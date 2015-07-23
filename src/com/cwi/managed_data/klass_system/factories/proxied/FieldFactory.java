package com.cwi.managed_data.klass_system.factories.proxied;

import com.cwi.managed_data.klass_system.Field;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Field.
 */
public class FieldFactory {

    public static Field make(Method _declaredFieldMethod, Class _ownerClass)
    {
        return (Field) Proxy.newProxyInstance(
                Field.class.getClassLoader(),
                new Class<?>[]{Field.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    String methodName = method.getName();

                    String fieldName = _declaredFieldMethod.getName();
                    Class fieldTypeClass = _declaredFieldMethod.getReturnType();

                    if (methodName.equals("name")) {
                        return fieldName;
                    }

                    if (methodName.equals("type")) {
                        return TypeFactory.make(fieldTypeClass);
                    }

                    if (methodName.equals("many")) {
                        return fieldTypeClass.isArray();
                    }

                    if (methodName.equals("optional")) {
                        // TODO: ?
                        return false;
                    }

                    if (methodName.equals("inverse")) {
                        // TODO: ?
                        return null;
                    }

                    if (methodName.equals("owner")) {
                        return KlassFactory.make(_ownerClass);
                    }

                    /**
                     * Implement for the Set
                     */
                    if (methodName.equals("hashCode")) {
                        return fieldName.hashCode();
                    }

                    return null;
                }
        );
    }

}