package nl.cwi.examples.ccconcerns.patterns.observer;

@FunctionalInterface
public interface Observe {
    void observe(Object obj, String field, Object value);
}