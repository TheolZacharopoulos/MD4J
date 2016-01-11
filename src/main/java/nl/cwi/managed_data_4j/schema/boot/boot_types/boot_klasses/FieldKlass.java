package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.*;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.implementation.AbstractKlass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class FieldKlass extends AbstractKlass {

    public FieldKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Field.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {
        final Field nameField = new NameField(schema, this);
        final Field ownerField = new OwnerField(schema, this);
        final Field typeField = new TypeField(schema, this);
        final Field manyField = new ManyField(schema, this);
        final Field optionalField = new OptionalField(schema, this);
        final Field inverseField = new InverseField(schema, this);

        return new LinkedHashSet<>(Arrays.asList(
                nameField,
                ownerField,
                typeField,
                manyField,
                optionalField,
                inverseField)
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
