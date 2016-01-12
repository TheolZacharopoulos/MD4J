package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

public interface SchemaFactory {
    Schema schema();
    Schema schema(Class<?>[] klassInterfaces, Type[] types);

    // Only primitives
    Klass klass(String name);

    Field field(String name, boolean many, boolean optional, boolean contain);
    // TODO: Add field()
}
