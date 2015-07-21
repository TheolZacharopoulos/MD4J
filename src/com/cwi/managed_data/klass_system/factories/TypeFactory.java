package com.cwi.managed_data.klass_system.factories;

import com.cwi.managed_data.klass_system.Type;

public class TypeFactory {

    public static Type make(Class _typeClass) {

        // Return the right type based on if it is Primitive or not.
        if (_typeClass.isPrimitive()) {
            return PrimitiveFactory.make(_typeClass);
        } else {
            return KlassFactory.make(_typeClass);
        }
    }
}