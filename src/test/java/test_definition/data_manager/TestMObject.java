package test_definition.data_manager;

import nl.cwi.java_managed_data.language.managed_object.MObject;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.java_managed_data.language.schema.models.definition.Klass;
import test_definition.schemas.Person;

public class TestMObject extends MObject {

    public TestMObject(Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
    }

    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        if ("value".equals(name)) {
            super._set(name, ((Integer)value) * 2);
        } else {
            super._set(name, value);
        }
    }

    @Override
    public Object _get(String name) {
        if ("age".equals(name)) {
            return 666;
        }
        return super._get(name);
    }

    public String justReturnWhatYouGet(String message) {
        ((Person) this.thisObject).name(message + "1");
        return message;
    }
}
