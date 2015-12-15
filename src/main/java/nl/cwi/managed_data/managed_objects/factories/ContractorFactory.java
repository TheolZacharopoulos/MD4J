package nl.cwi.managed_data.managed_objects.factories;

import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.managed_objects.ContractorManagedObject;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.roles.contract.Contractor;
import nl.cwi.managed_data.klass_system.factories.Factory;
import nl.cwi.managed_data.klass_system.models.Schema;
import nl.cwi.managed_data.managed_objects.ContractorManagedObject;
import nl.cwi.managed_data.managed_objects.ManagedObjectBase;
import nl.cwi.managed_data.roles.contract.Contractor;

@SuppressWarnings("unchecked")
public class ContractorFactory extends Factory {

    public ContractorFactory(Schema _schema) {
        super(_schema);

        // Add the contractor class in order to use it in the managed object.
        addProxiedInterface(Contractor.class);
    }

    @Override
    protected ManagedObjectBase createManagedObject(Object ..._inits) {
        return new ContractorManagedObject(schemaKlass);
    }
}
