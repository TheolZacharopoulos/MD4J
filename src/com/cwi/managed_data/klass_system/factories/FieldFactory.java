package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Field;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Field.
 */
public class FieldFactory {

    public static Field make(Method _fieldMethod) {
        return (Field) Proxy.newProxyInstance(
                Field.class.getClassLoader(),
                new Class<?>[]{Field.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    String methodName = method.getName();
                    String fieldName = _fieldMethod.getName();

                    if (methodName.equals("name")) {
                        return _fieldMethod.getName();
                    }

                    if (methodName.equals("type")) {
                        return KlassFactory.make(_fieldMethod.getReturnType()); // TODO: Type factory?;
                    }

                    if (methodName.equals("many")) {
                        // TODO:
                        return false;
                    }

                    if (methodName.equals("optional")) {
                        // TODO:
                        return false;
                    }

                    if (methodName.equals("inverse")) {
                        // TODO:
                        return null;
                    }

                    if (methodName.equals("owner")) {
                        // TODO:
                        return null;
                    }

                    /**
                     * Implement for the Set
                     */
                    if (methodName.equals("equals")) {
                        // TODO: This does not work.
                        String otherFieldName = (String) args[0];
                        return otherFieldName.equals(fieldName);
                    }

                    if (methodName.equals("hashCode")) {
                        return fieldName.hashCode();
                    }

                    return null;
                }
        );
    }

}