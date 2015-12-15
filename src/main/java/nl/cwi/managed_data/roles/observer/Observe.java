package nl.cwi.managed_data.roles.observer;

@FunctionalInterface
public interface Observe {
    void observe(Object obj, String field, Object value);
}