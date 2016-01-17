package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.*;

public interface SchemaFactory {
    Schema schema();

    Primitive primitive();

    Klass klass();

    Field field();
}
