package nl.cwi.managed_data_4j.schema.boot.boot_types.boot_klasses;

import nl.cwi.managed_data_4j.schema.boot.boot_fields.FieldsField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.SubKlassesField;
import nl.cwi.managed_data_4j.schema.boot.boot_fields.SupersField;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;

import java.util.*;

public class KlassKlass extends TypeKlass {

    public KlassKlass(Schema schema) {
        super(schema);
    }

    @Override
    public String name(String... name) {
        return Klass.class.getSimpleName();
    }

    @Override
    public Set<Field> fields(Field...field) {

        final Field supersField = new SupersField(schema, this);
        final Field subKlassesField = new SubKlassesField(schema, this);
        final Field fieldsField = new FieldsField(schema, this);

        // The fields of the klass klass
        Set<Field> klassKlassFields = new LinkedHashSet<>(Arrays.asList(
                supersField,
                subKlassesField,
                fieldsField)
        );

        // add the fields of the super class (Type)
        klassKlassFields.addAll(super.fields());

        // return all.
        return klassKlassFields;
    }

    @Override
    public Set<Klass> supers(Klass... supers) {
        return Collections.singleton(new TypeKlass(schema));
    }
}
