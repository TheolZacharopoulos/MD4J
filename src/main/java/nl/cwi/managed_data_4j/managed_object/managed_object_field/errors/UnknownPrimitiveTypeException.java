package nl.cwi.managed_data_4j.managed_object.managed_object_field.errors;

public class UnknownPrimitiveTypeException extends Exception {
    public UnknownPrimitiveTypeException() {
        super();
    }

    public UnknownPrimitiveTypeException(String message) {
        super(message);
    }
}
