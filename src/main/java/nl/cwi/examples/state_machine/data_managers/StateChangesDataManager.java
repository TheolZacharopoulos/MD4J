package nl.cwi.examples.state_machine.data_managers;

import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class StateChangesDataManager extends BasicDataManager {

    public StateChangesDataManager(Class<?> moSchemaFactoryClass, Schema schema) {
        super(moSchemaFactoryClass, schema, StateChangeManager.class);
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new StateChangesMObject(klass, _inits);
    }
}