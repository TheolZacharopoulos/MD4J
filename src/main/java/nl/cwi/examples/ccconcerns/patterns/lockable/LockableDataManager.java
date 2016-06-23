package nl.cwi.examples.ccconcerns.patterns.lockable;

import java.util.Arrays;
import java.util.Collections;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class LockableDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {

        // Add the Lockable class in order to use it in the managed object.
        return super.factory(factoryClass, schema, 
        		addAll(additionalInterfaces, Lockable.class));
        		
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new LockableMObject(klass, _inits);
    }
}