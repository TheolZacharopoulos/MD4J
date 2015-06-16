package com.cwi.managed_data.managed_objects;

import java.util.HashMap;

public class InitManagedObject extends LockableManagedObject {

    public InitManagedObject(Class _schema, HashMap<String, Object> _values) {
        super(_schema);

        // initialize
        for (String initValueName : _values.keySet()) {
            for (String existingValueName : values.keySet()) {
                if (initValueName.equals(existingValueName)) {
                    _set(existingValueName, _values.get(initValueName));
                }
            }
        }

        // lock, not changes on the values allowed after that, Immutable.
        lock();
    }
}
