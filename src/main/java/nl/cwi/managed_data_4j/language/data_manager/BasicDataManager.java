package nl.cwi.managed_data_4j.language.data_manager;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import nl.cwi.managed_data_4j.IFactory;
import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.primitives.PrimitivesManager;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

/**
 * Represents the basic data manager.
 * @author Theologos Zacharopoulos
 */
public class BasicDataManager implements IDataManager {


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
    	
    	List<Class<?>> ifaces = interfacesForSchema(schema);
    	ifaces.addAll(Arrays.asList(additionalInterfaces));
    	
        return (T) Proxy.newProxyInstance(
            factoryClass.getClassLoader(),
            new Class<?>[]{factoryClass},
            (proxy, method, args) ->
                createManagedObjectProxy(factoryClass, schema, 
                		onlyNonPrimitives(ifaces),
                		method, args)
        );
    }

    private List<Class<?>> interfacesForSchema(Schema schema){
    	return schema.klasses().stream()
    		.map((klass) -> { return klass.classOf(); })
    		.collect(Collectors.toList());
    }
    
    private List<Class<?>> onlyNonPrimitives(List<Class<?>> ifaces){
    	return ifaces.stream()
    		.filter((iface) -> { return (!PrimitivesManager.getInstance().isPrimitiveClass(iface)); })
    		.collect(Collectors.toList());
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
    private Object createManagedObjectProxy(
            Class<?> factoryClass, Schema schema, List<Class<?>> additionalIfaces,
            Method schemaFactoryCallingMethod, Object... inits)
    {
        // Find the schema klass
        final Klass schemaKlass = klassForMethod(schemaFactoryCallingMethod, schema);
        
        return Proxy.newProxyInstance(
        		// the class loader of the return type of the called method of the schema factory.
                factoryClass.getClassLoader(),

                // the interfaces that the Proxy will proxy.
                additionalIfaces.toArray(new Class[additionalIfaces.size()]),

                // proxy it to a new Managed Object
                createManagedObject(schemaKlass, inits));

    }
    
    private Klass klassForMethod(Method method, Schema schema){
    	return  schema.klasses().stream()
                .filter(klass -> klass.name().equals(method.getReturnType().getSimpleName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                "Error on klass extraction of class (" + method.getReturnType().getSimpleName() + ") "));
                //"from factory (" + factoryClass.getSimpleName() + ")"));
    }

    /**
     * This method should be implemented from the derived data manager
     * in order to create specific Managed Objects.
     *
     * @param klass the Managed Object schema Klass
     * @param inits a list of initialized props for the object construction.
     * @return a new Managed Object.
     */
    @Override
	public MObject createManagedObject(Klass klass, Object... inits) {
        return new MObject(klass, inits); // return a basic managed object
    }


    public static Class<?>[] addAll(Class<?>[] additional, Class<?> push) {
        Class<?>[] longer = new Class<?>[additional.length + 1];
        System.arraycopy(additional, 0, longer, 0, additional.length);
        longer[additional.length] = push;
        return longer;
    }
    
    
}
