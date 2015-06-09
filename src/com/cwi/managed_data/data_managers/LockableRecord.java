package com.cwi.managed_data.data_managers;

import java.lang.reflect.Proxy;

public class LockableRecord extends BasicRecord {

    private boolean isLocked = false;

    public static Object newInstance(Class _schema) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[]{_schema},
                new LockableRecord(_schema));
    }

    protected LockableRecord(Class _schema) {
        super(_schema);
    }

    protected void lock() {
        isLocked = true;
    }

    @Override
    protected void _set(String _name, Object _value) {
        if (isLocked) {
          throw new IllegalAccessError("Cannot change " + _name + " of locked object");
        }
        super._set(_name, _value);
    }
}