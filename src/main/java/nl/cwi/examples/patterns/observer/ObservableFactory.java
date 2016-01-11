package nl.cwi.examples.patterns.observer;

import nl.cwi.managed_data_4j.data_manager.AbstractFactory;
import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.lang.reflect.Method;

public class ObservableFactory extends AbstractFactory {

    @Override
    public void init(Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits) {
        super.init(moSchemaFactoryClass, schema, schemaFactoryCallingMethod, inits);

        // Add the observable class in order to use it in the managed object.
        addProxiedInterface(Observable.class);
    }

    @Override
    public MObject createManagedObject(Klass klass, Object... _inits) {
        return new ObservableMObject(klass, this, _inits);
    }
}