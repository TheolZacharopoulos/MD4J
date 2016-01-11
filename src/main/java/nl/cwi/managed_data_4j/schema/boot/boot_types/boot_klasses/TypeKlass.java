package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.NameField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.SchemaField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.TypesField;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.definition.Type;
import nl.cwi.managed_data_4j.schema.models.implementation.AbstractKlass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TypeKlass extends AbstractKlass {

    public TypeKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Type.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {
        final Field nameField = new NameField(schema, this);
        final Field schemaField = new SchemaField(schema, this, new TypesField(schema, this));

        return new HashSet<>(Arrays.asList(
                nameField,
                schemaField)
        );
    }

    @Override
    public Set<Klass> subklasses(Klass... subklasses) {
        return new HashSet<>(Arrays.asList(
                new PrimitiveKlass(schema),
                new KlassKlass(schema))
        );
    }
}
