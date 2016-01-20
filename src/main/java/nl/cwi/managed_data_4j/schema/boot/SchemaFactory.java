package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.*;

public interface SchemaFactory {
    Schema schema();

    Primitive primitive(String name);

    Klass klass();

    Field field();
    Field field(Boolean contain, Boolean key, Boolean many, String name, Boolean optional);
}
