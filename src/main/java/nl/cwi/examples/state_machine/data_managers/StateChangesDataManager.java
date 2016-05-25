package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.java_managed_data.IFactory;
import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

public class StateChangesDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {
        return super.factory(factoryClass, schema, StateChangeManager.class);
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new StateChangesMObject(klass, _inits);
    }
}