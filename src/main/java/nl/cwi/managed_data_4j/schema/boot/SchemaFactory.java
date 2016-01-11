package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public interface SchemaFactory {
    Schema schema();
    Schema schema(Class<?>[] klassInterfaces, Type[] types);
}
