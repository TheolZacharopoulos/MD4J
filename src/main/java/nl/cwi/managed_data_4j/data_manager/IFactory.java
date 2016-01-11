package nl.cwi.managed_data_4j.data_manager;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.lang.reflect.Method;

/**
 * The Data manager
 */
public interface IFactory {

    /**
     * Init properties of the data manager
     *
     * @param moSchemaFactoryClass the Class of the Schema-Factory.
     * @param schema the schema of the managed object which will be built.
     * @param schemaFactoryCallingMethod the method that is called to the schema factory
     *                                    in order to create a managed object instance.
     * @param inits (Optional) a list of initialization props for the object construction.
     */
    void init(Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits);

    /**
     * This method and should be implemented from the derived data_manager
     * in order to create specific Managed Objects.
     *
     * @param klass the Managed Object schema Klass
     * @param inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    MObject createManagedObject(Klass klass, Object... inits);

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
    Object createProxiedManagedObject(Class<?> moSchemaFactoryClass, Schema schema, Method schemaFactoryCallingMethod, Object... inits);
}
