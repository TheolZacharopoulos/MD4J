package nl.cwi.managed_data.roles.contract;

public interface Contractor {
    void require(Precondition precondition) throws ContractBrokenException;
    void ensure(Postcondition postcondition) throws ContractBrokenException;
}
