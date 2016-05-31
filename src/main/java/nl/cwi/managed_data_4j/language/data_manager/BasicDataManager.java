package nl.cwi.managed_data_4j.language.data_manager;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.language.primitives.PrimitivesManager;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents the basic data manager.
 * @author Theologos Zacharopoulos
 */
public class BasicDataManager implements IDataManager {

    // we need those in order to add the to the java Proxy.
    private Set<Class<?>> additionalInterfaces = new LinkedHashSet<>();

    /**
     * Used to build data managers which build managed objects.
     *
     * More specifically, it takes as an input the data manager, a factory class and a schema,
     * the data manager interprets the schema, and returns a Proxied Factory
     * that creates Managed Objects which are described with that schema.
     *
     * @param factoryClass the Class of the Schema-Factory.
     * @param schema the schema of the managed object which will be built.
     * @param additionalInterfaces (Optional) extra proxy Interfaces which will be attached to
     *                          the Dynamic proxy of the managed object.
     * @return a new factory which creates managed objects.
     */
    @SuppressWarnings("unchecked")
    public <T extends IFactory> T factory(Class<T> factoryClass, Schema schema, Class<?>... additionalInterfaces) {

        // add the extra proxy interfaces
        for (Class<?> proxyInterface : additionalInterfaces) {
            this.addProxyInterface(proxyInterface);
        }

        // add the klass interfaces of the schema
        for (Klass klass : schema.klasses()) {
            this.addProxyInterface(klass.classOf());
        }

        return (T) Proxy.newProxyInstance(
            factoryClass.getClassLoader(),
            new Class<?>[]{factoryClass},
            (proxy, method, args) ->
                createManagedObjectProxy(factoryClass, schema, method, args)
        );
    }

    /**
     * Creates a proxy for a managed object.
     * The reason of using a proxy here is to add methods
     * on the returned object since Java does not support dynamic method attachment.
     *
     * @param factoryClass the Class of the IFactory.
     * @param schema the schema of the managed object which will be built.
     * @param schemaFactoryCallingMethod the method that is called to the schema factory
     *                                    in order to create a managed object instance.
     * @param inits (Optional) a list of initialization props for the object construction.
     *
     * @return a new Proxied ManagedObject.
     */
    protected Object createManagedObjectProxy(
            Class<?> factoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits)
    {
        final Class<?> schemaFactoryCallingMethodClass = schemaFactoryCallingMethod.getReturnType();
        final ClassLoader schemaFactoryCallingMethodClassLoader = schemaFactoryCallingMethodClass.getClassLoader();

        // Find the schema klass
        final Klass schemaKlass = schema.klasses().stream()
            .filter(klass -> klass.name().equals(schemaFactoryCallingMethodClass.getSimpleName()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(
            "Error on klass extraction of class (" + schemaFactoryCallingMethodClass.getSimpleName() + ") " +
            "from factory (" + factoryClass.getSimpleName() + ")"));

        final MObject managedObject = this.createManagedObject(schemaKlass, inits);

        final Object proxyManagedObject = Proxy.newProxyInstance(
                schemaFactoryCallingMethodClassLoader,   // the class loader of the return type of the called method of the schema factory.
                additionalInterfaces.toArray(new Class[additionalInterfaces.size()]),  // the interfaces that the Proxy will proxy.
                managedObject  // proxy it to a new Managed Object
        );

        // wire the proxy object.
        managedObject.setProxy(proxyManagedObject);

        return proxyManagedObject;
    }

    /**
     * This method should be implemented from the derived data manager
     * in order to create specific Managed Objects.
     *
     * @param klass the Managed Object schema Klass
     * @param inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    protected MObject createManagedObject(Klass klass, Object... inits) {
        return new MObject(klass, inits); // return a basic managed object
    }

    /**
     * Helper to add new proxy interfaces, this is needed in case we need a new interface
     * to be proxy on the Managed Object.
     *
     * @param newInterface the interface to be added in the proxy interfaces list.
     */
    private void addProxyInterface(Class<?> newInterface) {
        if (newInterface != null && !PrimitivesManager.getInstance().isPrimitiveClass(newInterface)) {
            additionalInterfaces.add(newInterface);
        }
    }

    protected Class<?>[] pushKlassToProxyInterfaces(Class<?> push, Class<?>... additional) {
        Class<?>[] longer = new Class<?>[additional.length + 1];
        System.arraycopy(additional, 0, longer, 0, additional.length);
        longer[additional.length] = push;
        return longer;
    }
}
