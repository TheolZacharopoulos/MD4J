package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class StateChangesDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {
        return super.factory(factoryClass, schema, StateChangeManager.class);
    }

    @Override
	public MObject createManagedObject(Klass klass, Object... _inits) {
        return new StateChangesMObject(klass, _inits);
    }
}