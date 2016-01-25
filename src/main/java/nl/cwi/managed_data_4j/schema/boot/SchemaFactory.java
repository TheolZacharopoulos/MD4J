package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Primitive;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

public interface SchemaFactory {
    Schema schema();

    Primitive primitive(String name);

    Klass klass();

    Field field();
    Field field(Boolean contain, Boolean key, Boolean many, String name, Boolean optional);
}
