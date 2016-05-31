package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors;

public class UnknownTypeException extends Exception {
    public UnknownTypeException() {
        super();
    }

    public UnknownTypeException(String message) {
        super(message);
    }
}
