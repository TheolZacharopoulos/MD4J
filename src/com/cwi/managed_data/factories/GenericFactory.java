package com.cwi.managed_data.factories;

import com.cwi.managed_data.data_managers.DataManager;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class GenericFactory implements InvocationHandler {

    private final Class dataManagerClass;

    /**
     * Creates a new Factory instance.
     * @param _factoryClass which factory
     * @param _dataManagerClass with which dataManager
     * @return a new factory instance.
     */
    public static <T> T newFactory(Class _factoryClass, Class _dataManagerClass) {
        return (T) Proxy.newProxyInstance(
                _factoryClass.getClassLoader(),
                new Class<?>[]{_factoryClass},
                new GenericFactory(_dataManagerClass));
    }

    protected GenericFactory(Class _dataManagerClass) {
        this.dataManagerClass = _dataManagerClass;
    }

    private void checkConstructorsForParamsNumber(Class _class, int _paramsNumber)
            throws IllegalArgumentException
    {
        boolean found = false;
        Constructor[] constructors = _class.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() > _paramsNumber) {
                found = true;
            }
        }
        if (!found) {
            throw new IllegalArgumentException("No such constructor on " + _class.getName());
        }
    }

    private Map<String, Object> initializeValues(Class _schema, Object[] args) {
        Map<String, Object> values = new HashMap<>();

        for (int i = 0; i < args.length; i++) {

            // Get the parameter name from the schema.
            String parameterName = _schema.getMethods()[i].getName();

            // Get the parameter value from the argument.
            Object parameterValue = args[i];

            // Save the value.
            values.put(parameterName, parameterValue);
        }
        return values;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // Get the type which the factory returns (the schema)
        Class schema = method.getReturnType();

        DataManager dataManager;

        // Check if any extra parameters given
        if (args != null && args.length > 0) {

            // Check if there is such constructor in dataManagerClass
            // which have more than 1 parameters, throw if there is.
            checkConstructorsForParamsNumber(dataManagerClass, 1);

            // Get the initialization values
            Map<String, Object> values = initializeValues(schema, args);


            // Get the constructor of the data manager, create a new instance of the data manager.
            Constructor dataManagerConstructor = dataManagerClass.getConstructor(new Class[]{Class.class, values.getClass()});
            dataManager = (DataManager) dataManagerConstructor.newInstance(schema, values);

        // if no parameters given
        } else {

            // Get the constructor of the data manager, create a new instance of the data manager
            Constructor dataManagerConstructor = dataManagerClass.getConstructor(Class.class);
            dataManager = (DataManager) dataManagerConstructor.newInstance(schema);
        }

        // Create a new proxied object of the returned type,
        // with invocation handler the given dataManager
        return Proxy.newProxyInstance(
                schema.getClassLoader(),
                new Class<?>[]{schema},
                dataManager);
    }
}