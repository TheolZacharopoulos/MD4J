package nl.cwi.managed_data_4j.data_manager;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class BasicFactory implements IFactory {

    // the Class of the Schema-Factory.
    protected final Class<?> moSchemaFactoryClass;

    // the schema of the managed object which will be built.
    protected final Schema schema;

    // we need those in order to add the to the java Proxy.
    private Set<Class<?>> proxiedInterfaces = new HashSet<>();

    /**
     * Constructor
     * @param moSchemaFactoryClass the Class of the Schema-Factory.
     * @param schema the schema of the managed object which will be built.
     * @param proxiedInterfaces (Optional) extra proxied Interfaces which will be attached to
     *                          the Dynamic proxy of the managed object.
     */
    public BasicFactory(Class<?> moSchemaFactoryClass, Schema schema, Class<?>... proxiedInterfaces) {
        this.moSchemaFactoryClass = moSchemaFactoryClass;
        this.schema = schema;

        // add the extra proxied interfaces
        for (Class<?> proxiedInterface : proxiedInterfaces) {
            this.addProxiedInterface(proxiedInterface);
        }

        // add the klass interfaces of the schema
        this.schema.klasses().stream()
            .map(Klass::classOf)
            .forEach(this::addProxiedInterface);
    }

    /**
     * Used to build data managers which build managed objects.
     *
     * More specifically, it takes as an input the data manager, a factory class and a schema,
     * the data manager interprets the schema, and returns a Proxied Factory
     * that creates Managed Objects which are described with that schema.
     *
     * @return a new factory which creates managed objects.
     */
    @SuppressWarnings("unchecked")
    public <T> T make() {
        return (T) Proxy.newProxyInstance(
            moSchemaFactoryClass.getClassLoader(),
            new Class<?>[]{moSchemaFactoryClass},
            (proxy, method, args) ->
                this.createProxiedManagedObject(moSchemaFactoryClass, schema, method, args)
        );
    }

    /**
     * Proxies a managed object. The reason of using a proxy here is to add methods
     * on the returned object since Java does not support dynamic method attachment.
     *
     * @param moSchemaFactoryClass the Class of the Schema-Factory.
     * @param schema the schema of the managed object which will be built.
     * @param schemaFactoryCallingMethod the method that is called to the schema factory
     *                                    in order to create a managed object instance.
     * @param inits (Optional) a list of initialization props for the object construction.
     *
     * @return a new Proxied ManagedObject.
     */
    protected Object createProxiedManagedObject(
            Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits)
    {
        final Class<?> schemaFactoryCallingMethodClass = schemaFactoryCallingMethod.getReturnType();
        final ClassLoader schemaFactoryCallingMethodClassLoader = schemaFactoryCallingMethodClass.getClassLoader();

        // Find the schema klass
        Klass schemaKlass = schema.klasses().stream()
            .filter(klass -> klass.name().equals(schemaFactoryCallingMethodClass.getSimpleName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(
            "Error on klass extraction of class (" + schemaFactoryCallingMethodClass.getSimpleName() + ") " +
            "from factory (" + moSchemaFactoryClass.getSimpleName() + ")"));

        return Proxy.newProxyInstance(
                schemaFactoryCallingMethodClassLoader,   // the class loader of the return type of the called method of the schema factory.
                proxiedInterfaces.toArray(new Class[proxiedInterfaces.size()]),  // the interfaces that the Proxy will proxy.
                createManagedObject(schemaKlass, inits)  // proxy it to a new Managed Object
        );
    }

    /**
     * This method and should be implemented from the derived data_manager
     * in order to create specific Managed Objects.
     *
     * @param klass the Managed Object schema Klass
     * @param inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    protected MObject createManagedObject(Klass klass, Object... inits) {
        return new MObject(klass, this, inits); // return a basic managed object
    }

    /**
     * Helper to add new proxied interfaces, this is needed in case we need a new interface
     * to be proxied on the Managed Object.
     *
     * @param newInterface the interface to be added in the proxied interfaces list.
     */
    private void addProxiedInterface(Class<?> newInterface) {
        proxiedInterfaces.add(newInterface);
    }
}
