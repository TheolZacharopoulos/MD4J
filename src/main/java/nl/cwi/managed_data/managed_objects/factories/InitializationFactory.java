package nl.cwi.managed_data.managed_objects.factories;

import nl.cwi.managed_data.klass_system.models.Field;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.managed_objects.InitManagedObject;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.managed_objects.InitManagedObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class InitializationFactory extends Factory {

    public InitializationFactory(Schema _schema) {
        super(_schema);
    }

    @Override
    protected ManagedObjectBase createManagedObject(Object ..._inits) {

        // Initialize values from method arguments.
        HashMap<String, Object> values = new HashMap<String, Object>();

        Iterator<Field> fieldsIterator = this.schemaKlass.fields().iterator();
        Iterator<Object> valuesIterator = Arrays.asList(_inits).iterator();

        while (fieldsIterator.hasNext() && valuesIterator.hasNext()) {
            values.put(
                fieldsIterator.next().name(),
                valuesIterator.next()
            );
        }

        return new InitManagedObject(schemaKlass, values);
    }
}
