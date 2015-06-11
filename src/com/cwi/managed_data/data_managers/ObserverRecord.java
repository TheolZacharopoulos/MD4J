package com.cwi.managed_data.data_managers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObserverRecord extends BasicRecord {
    
    private List<Method> observers;

    public ObserverRecord(Class _schema) {
        super(_schema);
        observers = new ArrayList<Method>();
    }

    public void observe(Method _observer) {
        observers.add(_observer);
    }

    @Override
    protected void _set(String _name, Object _value) {
        super._set(_name, _value);
        for (Method observer : observers) {
            try {
                observer.invoke(this, _name, _value);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
