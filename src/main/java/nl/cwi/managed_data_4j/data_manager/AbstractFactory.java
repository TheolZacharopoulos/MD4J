package nl.cwi.managed_data_4j.data_manager;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 */
public abstract class AbstractFactory implements IFactory {

    // the Class of the Schema-Factory.
    protected Class<?> moSchemaFactoryClass;

    // the schema of the managed object which will be built.
    protected Schema schema;

    // we need those in order to add the to the java Proxy.
    private Set<Class<?>> proxiedInterfaces = new HashSet<>();

    @Override
    public void init(Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits) {
        this.moSchemaFactoryClass = moSchemaFactoryClass;
        this.schema = schema;

        // add the klass interfaces of the schema
        schema.klassInterfaces().forEach(this::addProxiedInterface);
    }

    public abstract MObject createManagedObject(Klass klass, Object... inits);

    @Override
    public Object createProxiedManagedObject(
            Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits)
    {
        this.init(moSchemaFactoryClass, schema, schemaFactoryCallingMethod, inits);

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
     * Helper to add new proxied interfaces, this is needed in case we need a new interface
     * to be proxied on the Managed Object.
     *
     * @param newInterface the interface to be added in the proxied interfaces list.
     */
    protected void addProxiedInterface(Class<?> newInterface) {
        proxiedInterfaces.add(newInterface);
    }
}