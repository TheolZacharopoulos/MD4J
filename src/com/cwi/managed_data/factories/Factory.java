package com.cwi.managed_data.factories;

import com.cwi.managed_data.data_managers.DataManager;

import java.lang.reflect.Proxy;

public class Factory {

    /**
     * Builds managed objects for specific schemas with data manager.
     * @param _schema the schema
     * @param _dataManager the data manager
     * @return a managed object.
     */
    public static Object make(Class _schema, DataManager _dataManager) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[] {_schema},
                _dataManager);
    }
}