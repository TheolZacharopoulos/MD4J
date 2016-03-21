package nl.cwi.managed_data_4j.ccconcerns.patterns.observer;

@FunctionalInterface
public interface Observe {
    void observe(Object obj, String field, Object value);
}