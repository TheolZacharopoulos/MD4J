package nl.cwi.managed_data_4j.schema.boot.boot_fields;

import nl.cwi.managed_data_4j.schema.boot.boot_types.boot_primitives.ClassPrimitive;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;

public class KlassInterfacesField extends AbstractField {

    public static final String NAME = "klassInterfaces";

    public KlassInterfacesField(Schema schema, Klass owner) {
        super(schema, owner);
    }

    @Override
    public String name(String... name) {
        return NAME;
    }

    @Override
    public Type type(Type... type) {
        return new ClassPrimitive(schema);
    }

    @Override
    public Boolean many(Boolean... many) {
        return true;
    }

    @Override
    public Boolean optional(Boolean... optional) {
        return true;
    }
}
