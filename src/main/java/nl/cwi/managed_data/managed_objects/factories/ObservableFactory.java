package nl.cwi.managed_data.managed_objects.factories;

import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.managed_objects.ObservableManagedObject;
import nl.cwi.managed_data.roles.observer.Observable;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.managed_objects.ObservableManagedObject;
import nl.cwi.managed_data.roles.observer.Observable;

@SuppressWarnings("unchecked")
public class ObservableFactory extends Factory {

    public ObservableFactory(Schema _schema) {
        super(_schema);

        // Add the observable class in order to use it in the managed object.
        addProxiedInterface(Observable.class);
    }

    @Override
    protected ManagedObjectBase createManagedObject(Object ..._inits) {
        return new ObservableManagedObject(schemaKlass);
    }
}
