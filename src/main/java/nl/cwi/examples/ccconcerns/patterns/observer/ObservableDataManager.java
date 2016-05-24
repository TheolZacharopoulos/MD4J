package nl.cwi.examples.ccconcerns.patterns.observer;

import nl.cwi.managed_data_4j.language.IFactory;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

public class ObservableDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {
        // Add the Observable class in order to use it in the managed object.
        return super.factory(factoryClass, schema, pushKlassToProxyInterfaces(Observable.class));
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new ObservableMObject(klass, _inits);
    }
}