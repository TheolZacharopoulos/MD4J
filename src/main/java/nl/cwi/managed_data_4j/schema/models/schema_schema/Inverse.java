package nl.cwi.managed_data_4j.schema.models.schema_schema;

public @interface Inverse {
    Class other();
    String field();
}
