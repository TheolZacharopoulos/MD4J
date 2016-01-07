package nl.cwi.examples.patterns.observer;

@FunctionalInterface
public interface Observe {
    void observe(Object obj, String field, Object value);
}