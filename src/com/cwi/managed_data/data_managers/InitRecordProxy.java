package com.cwi.managed_data.data_managers;

import java.lang.reflect.Proxy;
import java.util.Map;

public class InitRecordProxy extends LockableRecordProxy {

    public static Object newInstance(Class _schema, Map<String, Object> _values) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[]{_schema},
                new InitRecordProxy(_schema, _values));
    }

    private InitRecordProxy(Class _schema, Map<String, Object> _values) {
        super(_schema);
        for (String initValueName : _values.keySet()) {
            for (String existingValueName : values.keySet()) {
                if (initValueName.equals(existingValueName)) {
                    set(existingValueName, _values.get(initValueName));
                }
            }
        }

        lock();
    }
}