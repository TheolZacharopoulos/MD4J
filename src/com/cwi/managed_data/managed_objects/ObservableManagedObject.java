package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.roles.Observable;
import com.cwi.managed_data.roles.Observe;

import java.util.ArrayList;
import java.util.List;

public class ObservableManagedObject extends LockableManagedObject implements Observable {

    private List<Observe> observers;

    public ObservableManagedObject(Class _schema) {
        super(_schema);
        observers = new ArrayList<Observe>();
    }

    public void observe(Observe _observer) {
        observers.add(_observer);
    }

    @Override
    protected void _set(String _name, Object _value) {
        super._set(_name, _value);
        observers.forEach(
                (observer) -> observer.observe(this, _name, _value)
        );
    }
}
