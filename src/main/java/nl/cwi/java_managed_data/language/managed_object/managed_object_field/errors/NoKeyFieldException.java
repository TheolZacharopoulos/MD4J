package nl.cwi.java_managed_data.language.managed_object.managed_object_field.errors;

public class NoKeyFieldException extends Exception {
    public NoKeyFieldException() {
        super();
    }

    public NoKeyFieldException(String message) {
        super(message);
    }
}
