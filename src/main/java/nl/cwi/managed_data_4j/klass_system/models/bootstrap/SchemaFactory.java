package nl.cwi.managed_data_4j.klass_system.models.bootstrap;

import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.Type;

public interface SchemaFactory {
    Schema schema(Type... types);
}
