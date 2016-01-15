package nl.cwi.managed_data_4j.schema.boot;

import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.schema.models.implementation.*;

import java.util.*;

/**
 * This schema describes the schema of a schemaSchema (self description / MetaSchema)
 */
public class BootSchema extends SchemaImpl {

    public BootSchema() {
        final Schema schemaSchema = this;

        // ========================
        // Define the Schema Schema
        // ========================

        // ========================
        // * String Primitive
        final Primitive stringPrimitive = new PrimitiveImpl("String", schemaSchema);

        // ========================
        // * Boolean Primitive
        final Primitive booleanPrimitive = new PrimitiveImpl("Boolean", schemaSchema);

        // ========================
        // * Class Primitive
        final Primitive classPrimitive = new PrimitiveImpl("Class", schemaSchema);

        // ========================
        // * Schema Klass
        final Klass schemaKlass = new KlassImpl("Schema");
        schemaKlass.classOf(Schema.class);
        schemaKlass.schema(schemaSchema);

        final Field schemaKlassTypesField = new FieldImpl("types", true, false, true, true);
        schemaKlassTypesField.owner(schemaKlass);

//        final Field schemaKlassSchemaKlassField = new FieldImpl("schemaKlass", false, true, false, false);
//        schemaKlassSchemaKlassField.owner(schemaKlass);

        schemaKlass.fields(schemaKlassTypesField);

        // ========================
        // * Type Klass
        final Klass typeKlass = new KlassImpl("Type");
        typeKlass.classOf(Type.class);
        typeKlass.schema(schemaSchema);

        final Field typeKlassNameField = new FieldImpl("name", false, false, true, false);
        typeKlassNameField.owner(typeKlass);
        typeKlassNameField.type(stringPrimitive);

        final Field typeKlassSchemaField = new FieldImpl("schema", false, false, false, false);
        typeKlassSchemaField.owner(typeKlass);
        typeKlassSchemaField.type(schemaKlass);
        typeKlassSchemaField.inverse(schemaKlassTypesField);

        typeKlass.fields(typeKlassNameField, typeKlassSchemaField);

        // ========================
        // * Primitive Klass
        final Klass primitiveKlass = new KlassImpl("Primitive");
        primitiveKlass.classOf(Primitive.class);
        primitiveKlass.schema(schemaSchema);

        primitiveKlass.supers(typeKlass);

        final Field primitiveKlassNameField = new FieldImpl("name", false, false, true, false);
        primitiveKlassNameField.owner(primitiveKlass);
        primitiveKlassNameField.type(stringPrimitive);

        final Field primitiveKlassSchemaField = new FieldImpl("schema", false, false, false, false);
        primitiveKlassSchemaField.owner(primitiveKlass);
        primitiveKlassSchemaField.type(schemaKlass);
        primitiveKlassSchemaField.inverse(schemaKlassTypesField);

        primitiveKlass.fields(primitiveKlassNameField, primitiveKlassSchemaField);

        // ========================
        // * Klass Klass
        final Klass klassKlass = new KlassImpl("Klass");
        klassKlass.classOf(Klass.class);
        klassKlass.schema(schemaSchema);

//        schemaKlassSchemaKlassField.type(klassKlass);

        klassKlass.supers(typeKlass);
        typeKlass.subklasses(primitiveKlass, klassKlass);

        final Field klassKlassNameField = new FieldImpl("name", false, false, true, false);
        klassKlassNameField.owner(klassKlass);
        klassKlassNameField.type(stringPrimitive);

        final Field klassKlassSupersField = new FieldImpl("supers", true, false, false, false);
        klassKlassSupersField.owner(klassKlass);
        klassKlassSupersField.type(klassKlass);

        final Field klassKlassSubsField = new FieldImpl("subklasses", true, false, false, false);
        klassKlassSubsField.owner(klassKlass);
        klassKlassSubsField.type(klassKlass);
        klassKlassSubsField.inverse(klassKlassSupersField);

        final Field klassKlassFieldsField = new FieldImpl("fields", true, false, false, true);
        klassKlassFieldsField.owner(klassKlass);

        final Field klassKlassSchemaField = new FieldImpl("schema", false, false, false, false);
        klassKlassSchemaField.owner(klassKlass);
        klassKlassSchemaField.type(schemaKlass);
        klassKlassSchemaField.inverse(schemaKlassTypesField);

        final Field klassKlassClassOfField = new FieldImpl("classOf", false, false, false, false);
        klassKlassClassOfField.owner(klassKlass);
        klassKlassClassOfField.type(classPrimitive);

        klassKlass.fields(
            klassKlassNameField,
            klassKlassSupersField,
            klassKlassSubsField,
            klassKlassFieldsField,
            klassKlassSchemaField,
            klassKlassClassOfField
        );

        // ========================
        // * Field Klass
        final Klass fieldKlass = new KlassImpl("Field");
        fieldKlass.classOf(Field.class);
        klassKlass.schema(schemaSchema);

        klassKlassFieldsField.type(fieldKlass);

        final Field fieldKlassNameField = new FieldImpl("name", false, false, true, false);
        fieldKlassNameField.owner(fieldKlass);
        fieldKlassNameField.type(stringPrimitive);

        final Field fieldKlassOwnerField = new FieldImpl("owner", false, false, false, false);
        fieldKlassOwnerField.owner(fieldKlass);
        fieldKlassOwnerField.type(klassKlass);

        final Field fieldKlassTypeField = new FieldImpl("type", false, false, false, false);
        fieldKlassTypeField.owner(fieldKlass);
        fieldKlassTypeField.type(typeKlass);

        final Field fieldKlassManyField = new FieldImpl("many", false, false, false, false);
        fieldKlassManyField.owner(fieldKlass);
        fieldKlassManyField.type(booleanPrimitive);

        final Field fieldKlassOptionalField = new FieldImpl("optional", false, false, false, false);
        fieldKlassOptionalField.owner(fieldKlass);
        fieldKlassOptionalField.type(booleanPrimitive);

        final Field fieldKlassInverseField = new FieldImpl("inverse", false, true, false, false);
        fieldKlassInverseField.owner(fieldKlass);
        fieldKlassInverseField.type(fieldKlass);
        fieldKlassInverseField.inverse(fieldKlassInverseField);

        final Field fieldKlassKeyField = new FieldImpl("key", false, false, false, false);
        fieldKlassKeyField.owner(fieldKlass);
        fieldKlassKeyField.type(booleanPrimitive);

        final Field fieldKlassContentField = new FieldImpl("contain", false, false, false, false);
        fieldKlassContentField.owner(fieldKlass);
        fieldKlassContentField.type(booleanPrimitive);

        fieldKlass.fields(
            fieldKlassNameField,
            fieldKlassOwnerField,
            fieldKlassTypeField,
            fieldKlassManyField,
            fieldKlassOptionalField,
            fieldKlassKeyField,
            fieldKlassContentField,
            fieldKlassInverseField
        );

        // ========================
        // * Bootstrap definition
        this.types = new HashSet<>(Arrays.asList(
                schemaKlass,
                typeKlass,
                primitiveKlass,
                klassKlass,
                fieldKlass
        ));
    }
}
