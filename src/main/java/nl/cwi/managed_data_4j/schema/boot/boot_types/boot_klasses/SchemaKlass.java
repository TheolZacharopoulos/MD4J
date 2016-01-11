package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.KlassInterfacesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.KlassesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.PrimitivesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.TypesField;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.schema.models.implementation.AbstractKlass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class SchemaKlass extends AbstractKlass {

    public SchemaKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Schema.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {
        final Field typesField = new TypesField(schema, this);
        final Field klassInterfacesField = new KlassInterfacesField(schema, this);
        final Field primitivesField = new PrimitivesField(schema, this);
        final Field klassesField = new KlassesField(schema, this);

        return new LinkedHashSet<>(Arrays.asList(
            typesField,
            klassInterfacesField,
            primitivesField,
            klassesField
        ));
    }
}
