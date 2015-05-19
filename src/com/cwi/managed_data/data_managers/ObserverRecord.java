package com.cwi.managed_data.data_managers;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ObserverRecord extends BasicRecord {
    
    private List<Method> observers;
    
    public static Object newInstance(Class _schema) {
        return Proxy.newProxyInstance(
                _schema.getClassLoader(),
                new Class<?>[]{_schema},
                new ObserverRecord(_schema));
    }

    protected ObserverRecord(Class _schema) {
        super(_schema);
        observers = new ArrayList<Method>();
    }

    public void observe(Method _observer) {
        observers.add(_observer);
    }

    @Override
    protected void set(String _name, Object _value) {
        super.set(_name, _value);
        for (Method observer : observers) {
            try {
                observer.invoke(this, _name, _value);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
