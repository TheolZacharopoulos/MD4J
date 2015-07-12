package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.klass_system.Field;
import com.cwi.managed_data.klass_system.Klass;
import com.cwi.managed_data.klass_system.factories.FieldFactory;
import com.cwi.managed_data.klass_system.factories.KlassFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ManagedObj implements InvocationHandler {

    protected Map<String, Object> values;
    protected Klass schemaClass;

    public ManagedObj(Class _schema) {
        schemaClass = KlassFactory.make(_schema);
        values = new HashMap<>();

        schemaClass.fields()
            .forEach(
                    field -> {
                        if (!field.many()) {
                            // TODO: @values[field.name] = init;
                        } else {
                            // TODO:
                        }

                        createMethods(field.name());
                });
    }

    protected Object _get(String _name) throws NoSuchFieldError {
        //TODO: Something like that
        Field field = FieldFactory.make(schemaClass, _name);

        if (field == null) {
            throw new NoSuchFieldError("Accessing non-existant field " + _name);
        }
        return null;
    }

    protected void _set(String _name, Object _value) {
        //TODO: Something like that
        Field field = FieldFactory.make(schemaClass, _name);


    }

    protected void createMethods(String _name) {
        // TODO
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        return null;
    }
}
