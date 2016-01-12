package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.*;

public interface SchemaFactory {
    Schema schema();
    Schema schema(Class<?>[] klassInterfaces, Type[] types);

    Primitive primitive(String name);
    Klass klass(String name);

    Field field(String name);
    Field field(String name, boolean many, boolean optional);
}
