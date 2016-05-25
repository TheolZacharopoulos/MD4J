package nl.cwi.examples.ccconcerns.patterns.observer.lockable_observable;

import nl.cwi.examples.ccconcerns.patterns.lockable.Lockable;
import nl.cwi.examples.ccconcerns.patterns.observer.Observable;
import nl.cwi.java_managed_data.IFactory;
import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import nl.cwi.java_managed_data.language.schema.models.definition.Schema;

public class LockableObservableDataManager extends BasicDataManager {

    @Override
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {
        return super.factory(factoryClass, schema, Lockable.class, Observable.class);
    }

    @Override
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new LockableObservableMObject(klass, _inits);
    }
}