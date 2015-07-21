package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Type;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Converts an interface to a Managed Data Field.
 */
public class FieldFactory {

    public static Field make(String _fieldName, Type _fieldType, Class _ownerClass) {
        return (Field) Proxy.newProxyInstance(
                Field.class.getClassLoader(),
                new Class<?>[]{Field.class},

                // The invocation handler.
                (Object proxy, Method method, Object[] args) -> {

                    String methodName = method.getName();

                    if (methodName.equals("name")) {
                        return _fieldName;
                    }

                    if (methodName.equals("type")) {
                        return _fieldType;
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
                        return KlassFactory.make(_ownerClass);
                    }

                    /**
                     * Implement for the Set
                     */
                    if (methodName.equals("equals")) {
                        // TODO: This does not work.
                        String otherFieldName = (String) args[0];
                        return otherFieldName.equals(_fieldName);
                    }

                    if (methodName.equals("hashCode")) {
                        return _fieldName.hashCode();
                    }

                    return null;
                }
        );
    }

}