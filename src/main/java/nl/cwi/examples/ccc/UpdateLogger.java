package nl.cwi.examples.ccc;

public class UpdateLogger {
    public static void log(Object obj, String field, Object value) {
        System.out.println("\t\"Updated " + field + " to " + value + "\"");
    }
}
