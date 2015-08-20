package com.cwi.managed_data.managed_objects;

import com.cwi.managed_data.klass_system.models.Klass;
import com.cwi.managed_data.roles.contract.ContractBrokenException;
import com.cwi.managed_data.roles.contract.Contractor;
import com.cwi.managed_data.roles.contract.Postcondition;
import com.cwi.managed_data.roles.contract.Precondition;

import java.util.ArrayList;
import java.util.List;

public class ContractorManagedObject extends ManagedObjectBase implements Contractor {

    private List<Precondition> preconditions;
    private List<Postcondition> postconditions;

    public ContractorManagedObject(Klass _schemaKlass) {
        super(_schemaKlass);
        preconditions = new ArrayList<>();
        postconditions = new ArrayList<>();
    }

    public void require(Precondition _precondition) throws ContractBrokenException {
        preconditions.add(_precondition);
    }

    public void ensure(Postcondition _postcondition) throws ContractBrokenException {
        postconditions.add(_postcondition);
    }

    private void ensurePostconditions(String _name, Object _value) {
        postconditions.forEach(
            (postcondition) -> {
                try {
                    postcondition.ensure(this, _name, _value);
                } catch (Exception e) {
                    System.out.println("\t " + e.getMessage());
                }
            });
    }

    private void requirePreconditions(String _name, Object _value) {
        preconditions.forEach(
            (precondition) -> {
                try {
                    precondition.require(this, _name, _value);
                } catch (Exception e){
                    System.out.println("\t " + e.getMessage());
                }
            });
}

    @Override
    protected void _set(String _name, Object _value) {

        requirePreconditions(_name, _value);
        super._set(_name, _value);
        ensurePostconditions(_name, _value);
    }
}