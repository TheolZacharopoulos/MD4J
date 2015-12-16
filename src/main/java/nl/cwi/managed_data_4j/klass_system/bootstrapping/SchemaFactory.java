package nl.cwi.managed_data_4j.klass_system.bootstrapping;

import nl.cwi.managed_data_4j.klass_system.models.Klass;
import nl.cwi.managed_data_4j.klass_system.models.Schema;
import nl.cwi.managed_data_4j.klass_system.models.Type;

import java.util.Set;

public interface SchemaFactory {
    Schema schema(Set<Type> types, Set<Klass> klasses);
}
