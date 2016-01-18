package nl.cwi.examples.patterns.lockable;

import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

public class LockableFactory extends BasicFactory {

    public LockableFactory(Class<?> moSchemaFactoryClass, Schema schema) {

        // Add the Lockable class in order to use it in the managed object.
        super(moSchemaFactoryClass, schema, Lockable.class);
    }

    @Override
    public MObject createManagedObject(Klass klass, Object... _inits) {
        return new LockableMObject(klass, this, _inits);
    }
}