package nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors;

public class InvalidFieldValueException extends Exception {
    public InvalidFieldValueException() {
        super();
    }

    public InvalidFieldValueException(String message) {
        super(message);
    }
}
