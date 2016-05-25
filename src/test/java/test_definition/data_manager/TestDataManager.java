package test_definition.data_manager;

import nl.cwi.java_managed_data.IFactory;
import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

public class TestDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {
        return super.factory(factoryClass, schema, additionalInterfaces);
    }

    @Override
    protected TestMObject createManagedObject(Klass klass, Object... inits) {
        return new TestMObject(klass, inits);
    }
}
