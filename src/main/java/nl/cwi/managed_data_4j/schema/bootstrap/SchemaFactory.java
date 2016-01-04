package nl.cwi.managed_data_4j.schema.bootstrap;

import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public interface SchemaFactory {
    Schema schema(Type... types);
}
