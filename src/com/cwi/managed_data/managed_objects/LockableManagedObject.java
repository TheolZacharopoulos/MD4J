package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.klass_system.Klass;

public class LockableManagedObject extends ManagedObjectBase {

    private boolean isLocked = false;

    public LockableManagedObject(Klass _schemaKlass) {
        super(_schemaKlass);
    }

    protected void lock() {
        isLocked = true;
    }

    @Override
    protected void _set(String _name, Object _value) {
        if (isLocked) {
            throw new IllegalAccessError("Cannot change " + _name + " of locked object.");
        }
        super._set(_name, _value);
    }
}
