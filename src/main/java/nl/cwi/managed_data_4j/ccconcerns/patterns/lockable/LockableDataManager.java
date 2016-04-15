package nl.cwi.managed_data_4j.ccconcerns.patterns.lockable;

import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class LockableDataManager extends BasicDataManager {

    public LockableDataManager(Class<?> moSchemaFactoryClass, Schema schema) {

        // Add the Lockable class in order to use it in the managed object.
        super(moSchemaFactoryClass, schema, Lockable.class);
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new LockableMObject(klass, _inits);
    }
}