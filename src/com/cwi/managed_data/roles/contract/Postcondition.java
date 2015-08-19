package com.cwi.managed_data.roles.contract;

@FunctionalInterface
public interface Postcondition {
    void ensure(Object obj, String field, Object value) throws ContractBrokenException;
}