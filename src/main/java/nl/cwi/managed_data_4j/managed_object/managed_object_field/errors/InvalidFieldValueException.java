package nl.cwi.managed_data_4j.managed_object.managed_object_field.errors;

public class InvalidFieldValueException extends Exception {
    public InvalidFieldValueException() {
        super();
    }

    public InvalidFieldValueException(String message) {
        super(message);
    }
}
