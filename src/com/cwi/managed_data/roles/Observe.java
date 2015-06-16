package com.cwi.managed_data.roles;

@FunctionalInterface
public interface Observe {
    void observe(Object obj, String field, Object value);
}