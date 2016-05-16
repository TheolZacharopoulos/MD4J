package nl.cwi.examples.ccconcerns.aspects;

public class UpdateLogger {
    public static void log(Object obj, String field, Object value) {
        System.out.println(" > " + field + " updated to " + value);
    }
}
