package com.cwi.managed_data.data_managers;

import java.util.Map;

public class InitRecord extends LockableRecord {

    public InitRecord(Class _schema, Map<String, Object> _values) {
        super(_schema);

        for (String initValueName : _values.keySet()) {
            for (String existingValueName : values.keySet()) {
                if (initValueName.equals(existingValueName)) {
                    _set(existingValueName, _values.get(initValueName));
                }
            }
        }

        lock();
    }
}