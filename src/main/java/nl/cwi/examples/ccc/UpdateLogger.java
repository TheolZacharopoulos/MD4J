package nl.cwi.examples.ccc;

public class UpdateLogger {
    public static void log(Object obj, String field, Object value) {
        System.out.println(" > " + field + " updated to " + value);
    }
}
