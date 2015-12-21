package nl.cwi.managed_data_4j.data_managers;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.managed_objects.ManagedObjectBase;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 * The data manager (factory) interprets the schema to managed data.
 */
public class BasicFactory implements IFactory {

    protected Class<?> managedObjectInstanceFactoryClass;
    protected Schema schema;
    protected Object[] initializationValues;

    private Class<?>[] proxiedInterfaces = {};

    private BasicFactory(Class<?> _moInstanceFactoryClass, Schema _schema, Object... _inits) {
        this.managedObjectInstanceFactoryClass = _moInstanceFactoryClass;
        this.schema = _schema;
        this.initializationValues = _inits;

        // always add the schemaKlass Interface.
        schema.klassInterfaces()
            .forEach(this::addProxiedInterface);
    }

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
                new BasicFactory(_moInstanceFactoryClass, _schema, _inits));
    }

    /**
     * Proxies a managed object. The reason of using a proxy here is to add methods
     * on the returned object since Java does not support dynamic method attachment.
     *
     * @param _inits a list of initialization values for the object construction.
     * @return a new Proxied ManagedObject.
     */
    private Object createProxiedManagedObject(Object... _inits) {

        // FIXME: to be checked.
        Klass schemaKlass = schema.klasses().stream()
            .filter(klass ->
                schema.klassInterfaces().stream()
                    .map(Class::getSimpleName)
                    .anyMatch(klassInterfaceName -> klassInterfaceName.equals(klass.name()))
            )
            .findFirst()
            .orElseThrow(RuntimeException::new);

        // FIXME: hmmmmm
        ClassLoader classLoader = schema.klassInterfaces().stream().findFirst().get().getClassLoader();
        return Proxy.newProxyInstance(
                classLoader,
                proxiedInterfaces,
                createManagedObject(schemaKlass, _inits)
        );
    }

    /**
     * This method can (and should) be overridden from the derived data_managers in order to
     * create specific Managed Objects.
     *
     * @param _inits a list of initialized values for the object construction.
     * @return a new ManagedObjectBase.
     */
    protected ManagedObjectBase createManagedObject(Klass klass, Object... _inits) {
        return new ManagedObjectBase(klass, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return createProxiedManagedObject(initializationValues);
    }

    /**
     * Helper to add new proxied interfaces, this is needed in case we need a new interface
     * to be proxied on the Managed Object.
     *
     * @param _newInterface the interface to be added in the proxied interfaces list.
     */
    protected void addProxiedInterface(Class<?> _newInterface) {
        Class<?>[] result = Arrays.copyOf(proxiedInterfaces, proxiedInterfaces.length + 1);
        result[proxiedInterfaces.length] = _newInterface;
        proxiedInterfaces = result;
    }
}