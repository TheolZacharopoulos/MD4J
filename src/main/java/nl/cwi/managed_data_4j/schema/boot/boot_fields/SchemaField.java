package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses.SchemaKlass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class SchemaField extends AbstractField {

    public static final String NAME = "schema";

    private Field inverseTypesField;

    public SchemaField(Schema schema, Klass owner, Field inverseTypesField) {
        super(schema, owner);
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
    public Field inverse(Field... field) {
        return inverseTypesField;
    }
}
