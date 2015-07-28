package com.cwi.managed_data.managed_objects.factories;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Schema;
import com.cwi.managed_data.klass_system.factories.Factory;
import com.cwi.managed_data.managed_objects.InitManagedObject;
import com.cwi.managed_data.managed_objects.ManagedObjectBase;

import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class InitializationFactory extends Factory {

    public InitializationFactory(Schema _schema) {
        super(_schema);
    }

    @Override
    protected ManagedObjectBase createManagedObject(Object ...inits) {

        // Initialize values from method arguments.
        HashMap<String, Object> values = new HashMap<String, Object>();

        Iterator<Field> fieldsIterator = this.schemaKlass.fields().iterator();

        for (Object initValue : inits) {

            if (!fieldsIterator.hasNext()) {
                break;
            }

            // Get the field's name
            String parameterName = fieldsIterator.next().name();

            // Save the value.
            values.put(parameterName, initValue);
        }

        return new InitManagedObject(schemaKlass, values);
    }
}
