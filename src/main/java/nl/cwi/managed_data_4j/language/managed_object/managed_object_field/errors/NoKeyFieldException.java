package nl.cwi.managed_data_4j.language.managed_object.managed_object_field.errors;

public class NoKeyFieldException extends Exception {
    public NoKeyFieldException() {
        super();
    }

    public NoKeyFieldException(String message) {
        super(message);
    }
}
