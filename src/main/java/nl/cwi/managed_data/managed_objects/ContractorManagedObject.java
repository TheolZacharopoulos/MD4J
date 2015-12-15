package nl.cwi.managed_data.managed_objects;

import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.roles.contract.ContractBrokenException;
import nl.cwi.managed_data.roles.contract.Contractor;
import nl.cwi.managed_data.roles.contract.Postcondition;
import nl.cwi.managed_data.roles.contract.Precondition;
import nl.cwi.managed_data.klass_system.models.Klass;
import nl.cwi.managed_data.roles.contract.ContractBrokenException;

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

    private void ensurePostconditions(String _name, Object _value) throws ContractBrokenException {
        for (Postcondition postcondition : postconditions) {
            postcondition.ensure(this, _name, _value);
        }
    }

    private void requirePreconditions(String _name, Object _value) throws ContractBrokenException {
        for (Precondition precondition : preconditions) {
            precondition.require(this, _name, _value);
        }
    }

    @Override
    protected void _set(String _name, Object _value) {

        requirePreconditions(_name, _value);
        super._set(_name, _value);
        ensurePostconditions(_name, _value);
    }
}