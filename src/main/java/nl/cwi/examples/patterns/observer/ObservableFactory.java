package nl.cwi.examples.patterns.observer;

import nl.cwi.managed_data_4j.data_managers.BasicFactory;
import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.lang.reflect.Proxy;

public class ObservableFactory extends BasicFactory {

    // TODO: Find a way to avoid this repetition
    /**
     * Used to build managed objects.
     * More specifically, it takes as an input a factory class and a schema, and interprets the schema,
     * since it is a Data-Manager, and returns a Proxied Factory that created Managed Objects which are
     * described with that schema.
     *
     * @param _moInstanceFactoryClass The class of the managedObject instance factory.
     * @param _schema The schema of the managed object instance.
     * @param _inits (Optional) To support stacking of data managers the constructor of Factory (data manager)
     *               needs to accept a dictionary of initialization parameters for overriding
     *               data managers that require different inputs.
     *
     * @return a new factory which creates managed objects.
     */
    public static <T> T make(Class<?> _moInstanceFactoryClass, Schema _schema, Object... _inits) {
        return (T) createProxiedManagedObjectInstanceFactory(_moInstanceFactoryClass, _schema, _inits);
    }

    private static Object createProxiedManagedObjectInstanceFactory(
            Class<?> _moInstanceFactoryClass,
            Schema _schema,
            Object... _inits)
    {
        return Proxy.newProxyInstance(
            _moInstanceFactoryClass.getClassLoader(),
            new Class<?>[]{_moInstanceFactoryClass},

            // TODO: This repetition is because of that.
            new ObservableFactory(_moInstanceFactoryClass, _schema, _inits));
    }

    // TODO: Keep only those
    protected ObservableFactory(Class<?> _moInstanceFactoryClass, Schema _schema, Object... _inits) {
        super(_moInstanceFactoryClass, _schema, _inits);

        // Add the observable class in order to use it in the managed object.
        addProxiedInterface(Observable.class);
    }

    /**
     * This method can (and should) be overridden from the derived data_managers in order to
     * create specific Managed Objects.
     *
     * @param _inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    protected MObject createManagedObject(Klass klass, Object... _inits) {
        return new ObservableMObject(klass, this, _inits);
    }
}