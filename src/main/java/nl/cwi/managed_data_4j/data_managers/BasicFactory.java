package nl.cwi.managed_data_4j.data_managers;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.managed_object.ManagedObjectBase;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 * The data manager (factory) interprets the schema to managed data.
 */
@SuppressWarnings("unchecked")
public class BasicFactory implements IFactory {

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
        return (T) Proxy.newProxyInstance(
            _moInstanceFactoryClass.getClassLoader(), // The schema factory's class bootloader.
            new Class<?>[]{_moInstanceFactoryClass},  // The schema factory's class.
            new BasicFactory(_moInstanceFactoryClass, _schema, _inits) // Proxy the class's invocation handler.
        );
    }

    // Invocation handler Definition.
    protected Class<?> managedObjectInstanceFactoryClass;
    protected Schema schema;
    private Class<?>[] proxiedInterfaces = {};

    private BasicFactory(Class<?> _moInstanceFactoryClass, Schema _schema, Object... _inits) {
        this.managedObjectInstanceFactoryClass = _moInstanceFactoryClass;
        this.schema = _schema;

        // always add the schemaKlass Interface.
        if (schema.klassInterfaces() != null) {
            schema.klassInterfaces()
                .forEach(this::addProxiedInterface);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return createProxiedManagedObject(method, args);
    }

    /**
     * Proxies a managed object. The reason of using a proxy here is to add methods
     * on the returned object since Java does not support dynamic method attachment.
     *
     * @param _schemaFactoryCallingMethod the method that is called to the schema factory
     *                                    in order to create a managed object instance.
     * @param _inits a list of initialization props for the object construction.
     * @return a new Proxied ManagedObject.
     */
    private Object createProxiedManagedObject(Method _schemaFactoryCallingMethod, Object... _inits) {
        final Class<?> schemaFactoryCallingMethodClass = _schemaFactoryCallingMethod.getReturnType();
        final ClassLoader schemaFactoryCallingMethodClassLoader = schemaFactoryCallingMethodClass.getClassLoader();

        // Find the schema klass
        Klass schemaKlass =
            schema.klasses().stream()
            .filter(klass -> klass.name().equals(schemaFactoryCallingMethodClass.getSimpleName()))
            .findFirst()
            .orElseThrow(RuntimeException::new);

        return Proxy.newProxyInstance(
            schemaFactoryCallingMethodClassLoader, // the class loader of the return type of the called method of the schema factory.
            proxiedInterfaces,
            createManagedObject(schemaKlass, _inits) // proxy it to a new Managed Object
        );
    }

    /**
     * This method can (and should) be overridden from the derived data_managers in order to
     * create specific Managed Objects.
     *
     * @param _inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    protected ManagedObjectBase createManagedObject(Klass klass, Object... _inits) {
        // TODO: switch to MObject
        return new ManagedObjectBase(klass, this, _inits);
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