package nl.cwi.managed_data.roles.contract;

@FunctionalInterface
public interface Precondition {
    void require(Object obj, String field, Object value) throws ContractBrokenException;
}