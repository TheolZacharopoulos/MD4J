package nl.cwi.examples.ccconcerns.patterns.observer;

@FunctionalInterface
public interface Observer {
    void observe(Object obj, String fieldName, Object value);
}