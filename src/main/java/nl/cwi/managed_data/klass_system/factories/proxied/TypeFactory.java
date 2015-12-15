package nl.cwi.managed_data.klass_system.factories.proxied;

import nl.cwi.managed_data.klass_system.models.Type;
import nl.cwi.managed_data.klass_system.models.Type;

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