package nl.cwi.managed_data.klass_system.factories;

import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;

import java.lang.reflect.Proxy;

import java.util.Arrays;

/**
 * A factory should be instantiated with a schema in order to
 * query the schema to know what fields exist etc.
 */
@SuppressWarnings("unchecked")
public class Factory {

    protected final Schema schema;
    protected final Klass schemaKlass;
    protected final Class klassInterface;

    private Class<?>[] proxiedInterfaces = {};

    public Factory(Schema _schema) {
        this.schema = _schema;

        // TODO: Why is this a Set ???
        this.schemaKlass = schema.classes().stream().findFirst().get();
        this.klassInterface = schemaKlass.klassInterface();

        // always add the klass Interface.
        addProxiedInterface(klassInterface);
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

    /**
     * Returns a proxied ManagedObject which is based on the schema klass.
     * This method is used form the clients in order to get a managed object.
     *
     * @param _inits (Optional) a list of initialization values for the object construction.
     * @return a new managed object, (type erasure with casting for readability)
     */
    public <T> T make(Object ..._inits) {
        return (T) createProxiedManagedObject(_inits);
    }

    /**
     * Proxies a managed object. The reason of using a proxy here is to add methods
     * on the returned object since Java does not support dynamic method attachment.
     *
     * @param _inits a list of initialized values for the object construction.
     * @return a new Proxied ManagedObject.
     */
    private Object createProxiedManagedObject(Object ..._inits) {
        return Proxy.newProxyInstance(
                klassInterface.getClassLoader(),
                proxiedInterfaces,
                createManagedObject(_inits)
        );
    }

    /**
     * This method can (and should) be overridden from the derived factories in order to
     * create specific Managed Objects.
     *
     * @param _inits a list of initialized values for the object construction.
     * @return a new ManagedObjectBase.
     */
    protected ManagedObjectBase createManagedObject(Object ..._inits) {
        return new ManagedObjectBase(schemaKlass);
    }
}