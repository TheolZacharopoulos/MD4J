package nl.cwi.examples.ccconcerns.patterns.simplelogging;

import java.util.Arrays;
import java.util.List;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.InvalidFieldValueException;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors.NoKeyFieldException;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;

public class LoggingMObject extends MObject{
	private List<String> fieldsToLog;
	
    public LoggingMObject(List<String> fieldsToLog, Klass schemaKlass, Object... initializers) {
        super(schemaKlass, initializers);
        this.fieldsToLog = fieldsToLog;
    }

    @Override
    public void _set(String name, Object value) throws NoSuchFieldError, InvalidFieldValueException, NoKeyFieldException {
        if (fieldsToLog.contains(name))
        	System.out.println(name + " = " + value);
        super._set(name, value);
    }
    
    @Override
    public String toString(){
    	return "a LoggingMObject";
    }
}