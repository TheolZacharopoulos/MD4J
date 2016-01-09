package nl.cwi.managed_data_4j.data_managers;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;

import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class DataManagerFactory {

    /**
     * Used to build data managers which build managed objects.
     *
     * More specifically, it takes as an input the data manager, a factory class and a schema,
     * the data manager interprets the schema, and returns a Proxied Factory
     * that creates Managed Objects which are described with that schema.
     *
     * @param datamanager the data manager.
     * @param moInstanceFactoryClass The class of the managedObject instance factory.
     * @param schema The schema of the managed object instance.
     *
     * @return a new factory which creates managed objects.
     */
    public static <T> T make(IFactory datamanager, Class<?> moInstanceFactoryClass, Schema schema)
    {
        return (T) Proxy.newProxyInstance(
            moInstanceFactoryClass.getClassLoader(),
            new Class<?>[]{moInstanceFactoryClass},
            (proxy, method, args) -> datamanager.createProxiedManagedObject(moInstanceFactoryClass, schema, method, args)
        );
    }
}
