package com.cwi.managed_data.managed_objects.factories;

import com.cwi.managed_data.klass_system.models.Schema;
import com.cwi.managed_data.klass_system.factories.Factory;
import com.cwi.managed_data.managed_objects.ManagedObjectBase;
import com.cwi.managed_data.managed_objects.ObservableManagedObject;
import com.cwi.managed_data.roles.Observable;

@SuppressWarnings("unchecked")
public class ObservableFactory extends Factory {

    public ObservableFactory(Schema _schema) {
        super(_schema);

        // Add the observable class in order to use it in the managed object.
        addProxiedInterface(Observable.class);
    }

    @Override
    protected ManagedObjectBase createManagedObject(Object ...inits) {
        return new ObservableManagedObject(schemaKlass);
    }
}
