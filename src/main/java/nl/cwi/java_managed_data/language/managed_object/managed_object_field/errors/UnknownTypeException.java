package nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors;

public class UnknownTypeException extends Exception {
    public UnknownTypeException() {
        super();
    }

    public UnknownTypeException(String message) {
        super(message);
    }
}
