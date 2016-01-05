package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_klasses.SchemaKlass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class SchemaField implements Field {

    public static final String NAME = "schema";

    private Schema schema;
    private Klass owner;
    private Field inverseTypesField;

    public SchemaField(Schema schema, Klass owner, Field inverseTypesField) {
        this.schema = schema;
        this.owner = owner;
        this.inverseTypesField = inverseTypesField;
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Type type(Type... type) {
        return new SchemaKlass(schema);
    }

    @Override
    public Boolean many(Boolean... many) {
        return false;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return false;
    }

    @Override
    public Field inverse(Field... field) {
        return inverseTypesField;
    }

    @Override
    public Klass owner(Klass... owner) {
        return this.owner;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void setOwner(Klass owner) {
        this.owner = owner;
    }

    public void setInverseTypesField(Field inverseTypesField) {
        this.inverseTypesField = inverseTypesField;
    }
}
