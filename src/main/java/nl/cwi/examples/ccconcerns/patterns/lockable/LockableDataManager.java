package nl.cwi.examples.ccconcerns.patterns.lockable;

import nl.cwi.java_managed_data.IFactory;
import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

public class LockableDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {

        // Add the Lockable class in order to use it in the managed object.
        return super.factory(factoryClass, schema, pushKlassToProxyInterfaces(Lockable.class, additionalInterfaces));
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new LockableMObject(klass, _inits);
    }
}