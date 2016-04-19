package nl.cwi.managed_data_4j.language.schema.boot;

import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.schema.models.implementation.FieldImpl;
import nl.cwi.managed_data_4j.language.schema.models.implementation.KlassImpl;
import nl.cwi.managed_data_4j.language.schema.models.implementation.PrimitiveImpl;
import nl.cwi.managed_data_4j.language.schema.models.implementation.SchemaImpl;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * This schema describes the schema of a schemaSchema (self description / MetaSchema)
 * @author Theologos Zacharopoulos
 */
public class BootSchema extends SchemaImpl {

    public BootSchema() {
        final Schema schemaSchema = this;

        // ========================
        // Define the Schema Schema
        // ========================

        // ========================
        // * String Primitive
        final Primitive stringPrimitive = new PrimitiveImpl("String", schemaSchema, String.class);

        // ========================
        // * Boolean Primitive
        final Primitive booleanPrimitive = new PrimitiveImpl("Boolean", schemaSchema, Boolean.class);

        // ========================
        // * Class Primitive
        final Primitive classPrimitive = new PrimitiveImpl("Class", schemaSchema, Class.class);

        // ========================
        // * Schema Klass
        final Klass schemaKlass = new KlassImpl("Schema");
        schemaKlass.classOf(Schema.class);
        schemaKlass.schema(schemaSchema);

        final Field schemaKlassTypesField = new FieldImpl("types", true, false, false, true);
        schemaKlassTypesField.owner(schemaKlass);

        final Field schemaKlassSchemaKlassField = new FieldImpl("schemaKlass", false, false, false, false);
        schemaKlassSchemaKlassField.owner(schemaKlass);

        schemaKlass.fields(
            schemaKlassSchemaKlassField,
            schemaKlassTypesField);

        // ========================
        // * Type Klass
        final Klass typeKlass = new KlassImpl("Type");
        typeKlass.classOf(Type.class);
        typeKlass.schema(schemaSchema);

        final Field typeKlassNameField = new FieldImpl("name", false, false, true, false);
        typeKlassNameField.owner(typeKlass);
        typeKlassNameField.type(stringPrimitive);

        // wiring
        typeKlass.key(typeKlassNameField);
        schemaKlassTypesField.type(typeKlass);

        final Field typeKlassSchemaField = new FieldImpl("schema", false, false, false, false);
        typeKlassSchemaField.owner(typeKlass);
        typeKlassSchemaField.type(schemaKlass);
        typeKlassSchemaField.inverse(schemaKlassTypesField);

        final Field typeKlassSchemaKlassField = new FieldImpl("schemaKlass", false, false, false, false);
        typeKlassSchemaKlassField.owner(typeKlass);

        final Field typeKlassClassOfField = new FieldImpl("classOf", false, false, false, false);
        typeKlassClassOfField.owner(typeKlass);
        typeKlassClassOfField.type(classPrimitive);

        final Field typeKlassKeyField = new FieldImpl("key", false, true, false, false);
        typeKlassKeyField.owner(typeKlass);

        typeKlass.fields(
            typeKlassClassOfField,
            typeKlassNameField,
            typeKlassKeyField,
            typeKlassSchemaField,
            typeKlassSchemaKlassField);

        // ========================
        // * Primitive Klass
        final Klass primitiveKlass = new KlassImpl("Primitive");
        primitiveKlass.classOf(Primitive.class);
        primitiveKlass.schema(schemaSchema);

        primitiveKlass.supers(typeKlass);

        final Field primitiveKlassNameField = new FieldImpl("name", false, false, true, false);
        primitiveKlassNameField.owner(primitiveKlass);
        primitiveKlassNameField.type(stringPrimitive);

        // wiring
        primitiveKlass.key(primitiveKlassNameField);

        final Field primitiveKlassSchemaField = new FieldImpl("schema", false, false, false, false);
        primitiveKlassSchemaField.owner(primitiveKlass);
        primitiveKlassSchemaField.type(schemaKlass);
        primitiveKlassSchemaField.inverse(schemaKlassTypesField);

        final Field primitiveKlassSchemaKlassField = new FieldImpl("schemaKlass", false, false, false, false);
        primitiveKlassSchemaKlassField.owner(primitiveKlass);

        final Field primitiveKlassClassOfField = new FieldImpl("classOf", false, false, false, false);
        primitiveKlassClassOfField.owner(primitiveKlass);
        primitiveKlassClassOfField.type(classPrimitive);

        final Field primitiveKlassKeyField = new FieldImpl("key", false, true, false, false);
        primitiveKlassKeyField.owner(primitiveKlass);

        primitiveKlass.fields(
            primitiveKlassClassOfField,
            primitiveKlassNameField,
            primitiveKlassKeyField,
            primitiveKlassSchemaField,
            primitiveKlassSchemaKlassField);

        // ========================
        // * Klass Klass
        final Klass klassKlass = new KlassImpl("Klass");
        klassKlass.classOf(Klass.class);
        klassKlass.schema(schemaSchema);

        // wiring
        schemaKlassSchemaKlassField.type(klassKlass);
        typeKlassSchemaKlassField.type(klassKlass);
        primitiveKlassSchemaKlassField.type(klassKlass);

        klassKlass.supers(typeKlass);
        typeKlass.subKlasses(primitiveKlass, klassKlass);

        final Field klassKlassNameField = new FieldImpl("name", false, false, true, false);
        klassKlassNameField.owner(klassKlass);
        klassKlassNameField.type(stringPrimitive);

        // wiring
        klassKlass.key(klassKlassNameField);

        final Field klassKlassSupersField = new FieldImpl("supers", true, false, false, false);
        klassKlassSupersField.owner(klassKlass);
        klassKlassSupersField.type(klassKlass);

        final Field klassKlassSubsField = new FieldImpl("subKlasses", true, false, false, false);
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

        final Field klassKlassSchemaKlassField = new FieldImpl("schemaKlass", false, false, false, false);
        klassKlassSchemaKlassField.type(klassKlass);
        klassKlassSchemaKlassField.owner(klassKlass);

        final Field klassKlassKeyField = new FieldImpl("key", false, true, false, false);
        klassKlassKeyField.owner(klassKlass);

        klassKlass.fields(
            klassKlassClassOfField,
            klassKlassNameField,
            klassKlassKeyField,
            klassKlassFieldsField,
            klassKlassSupersField,
            klassKlassSubsField,
            klassKlassSchemaField,
            klassKlassSchemaKlassField);

        // ========================
        // * Field Klass
        final Klass fieldKlass = new KlassImpl("Field");
        fieldKlass.classOf(Field.class);
        klassKlass.schema(schemaSchema);

        final Field fieldKlassNameField = new FieldImpl("name", false, false, true, false);
        fieldKlassNameField.owner(fieldKlass);
        fieldKlassNameField.type(stringPrimitive);

        fieldKlass.key(fieldKlassNameField);

        // wiring
        klassKlassFieldsField.type(fieldKlass);
        typeKlassKeyField.type(fieldKlass);
        primitiveKlassKeyField.type(fieldKlass);
        klassKlassKeyField.type(fieldKlass);

        final Field fieldKlassOwnerField = new FieldImpl("owner", false, false, false, false);
        fieldKlassOwnerField.owner(fieldKlass);
        fieldKlassOwnerField.type(klassKlass);
        fieldKlassOwnerField.inverse(klassKlassFieldsField);

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

        final Field fieldKlassContainField = new FieldImpl("contain", false, false, false, false);
        fieldKlassContainField.owner(fieldKlass);
        fieldKlassContainField.type(booleanPrimitive);

        final Field fieldKlassSchemaKlassField = new FieldImpl("schemaKlass", false, false, false, false);
        fieldKlassSchemaKlassField.type(klassKlass);
        fieldKlassSchemaKlassField.owner(fieldKlass);

        fieldKlass.fields(
            fieldKlassContainField,
            fieldKlassKeyField,
            fieldKlassManyField,
            fieldKlassNameField,
            fieldKlassOptionalField,
            fieldKlassTypeField,
            fieldKlassOwnerField,
            fieldKlassInverseField,
            fieldKlassSchemaKlassField);

        // ========================
        // * set the schemaKlasses
        // - schema schema
        this.schemaKlass = schemaKlass;

        // - primitives
        stringPrimitive.schemaKlass(primitiveKlass);
        booleanPrimitive.schemaKlass(primitiveKlass);
        classPrimitive.schemaKlass(primitiveKlass);

        // - klasses
        schemaKlass.schemaKlass(klassKlass);
        typeKlass.schemaKlass(klassKlass);
        primitiveKlass.schemaKlass(klassKlass);
        klassKlass.schemaKlass(klassKlass);
        fieldKlass.schemaKlass(klassKlass);

        // - fields
        //   - type
        typeKlassNameField.schemaKlass(fieldKlass);
        typeKlassSchemaField.schemaKlass(fieldKlass);
        typeKlassSchemaKlassField.schemaKlass(fieldKlass);
        typeKlassClassOfField.schemaKlass(fieldKlass);

        //   - primitive
        primitiveKlassClassOfField.schemaKlass(fieldKlass);
        primitiveKlassNameField.schemaKlass(fieldKlass);
        primitiveKlassSchemaField.schemaKlass(fieldKlass);
        primitiveKlassSchemaKlassField.schemaKlass(fieldKlass);

        //   - klass
        klassKlassClassOfField.schemaKlass(fieldKlass);
        klassKlassNameField.schemaKlass(fieldKlass);
        klassKlassFieldsField.schemaKlass(fieldKlass);
        klassKlassSchemaField.schemaKlass(fieldKlass);
        klassKlassSupersField.schemaKlass(fieldKlass);
        klassKlassSubsField.schemaKlass(fieldKlass);
        klassKlassSchemaKlassField.schemaKlass(fieldKlass);

        //   - field
        fieldKlassContainField.schemaKlass(fieldKlass);
        fieldKlassKeyField.schemaKlass(fieldKlass);
        fieldKlassManyField.schemaKlass(fieldKlass);
        fieldKlassNameField.schemaKlass(fieldKlass);
        fieldKlassOptionalField.schemaKlass(fieldKlass);
        fieldKlassTypeField.schemaKlass(fieldKlass);
        fieldKlassInverseField.schemaKlass(fieldKlass);
        fieldKlassOwnerField.schemaKlass(fieldKlass);
        fieldKlassSchemaKlassField.schemaKlass(fieldKlass);

        // ========================
        // * Bootstrap definition
        this.types = new LinkedHashSet<>(Arrays.asList(
                schemaKlass,
                typeKlass,
                primitiveKlass,
                klassKlass,
                fieldKlass,
                stringPrimitive,
                classPrimitive,
                booleanPrimitive
        ));
    }
}
