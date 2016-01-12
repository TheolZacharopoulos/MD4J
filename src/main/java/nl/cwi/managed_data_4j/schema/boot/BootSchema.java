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
        // * Schema Klass
        final Klass schemaKlass = new KlassImpl("Schema");
        schemaKlass.schema(schemaSchema);

        final Field schemaKlassTypesField = new FieldImpl("types", true, false);
        schemaKlassTypesField.owner(schemaKlass);

        // TODO: Remove this
        final Field schemaKlassklassInterfacesField = new FieldImpl("klassInterfaces", true, false);
        schemaKlassklassInterfacesField.owner(schemaKlass);

        schemaKlass.fields(schemaKlassTypesField, schemaKlassklassInterfacesField);

        // ========================
        // * Type Klass
        final Klass typeKlass = new KlassImpl("Type");
        typeKlass.schema(schemaSchema);

        final Field typeKlassNameField = new FieldImpl("name", false, false);
        // typeKlassNameField.key(true);
        typeKlassNameField.owner(typeKlass);
        typeKlassNameField.type(stringPrimitive);

        final Field typeKlassSchemaField = new FieldImpl("schema", false, false);
        typeKlassSchemaField.owner(typeKlass);
        typeKlassSchemaField.type(schemaKlass);
        typeKlassSchemaField.inverse(schemaKlassTypesField);

        typeKlass.fields(typeKlassNameField, typeKlassSchemaField);

        // ========================
        // * Primitive Klass
        final Klass primitiveKlass = new KlassImpl("Primitive");
        primitiveKlass.schema(schemaSchema);

        primitiveKlass.supers(typeKlass);

        final Field primitiveKlassNameField = new FieldImpl("name", false, false);
        // primitiveKlassNameField.key(true);
        primitiveKlassNameField.owner(primitiveKlass);
        primitiveKlassNameField.type(stringPrimitive);

        final Field primitiveKlassSchemaField = new FieldImpl("schema", false, false);
        primitiveKlassSchemaField.owner(primitiveKlass);
        primitiveKlassSchemaField.type(schemaKlass);
        primitiveKlassSchemaField.inverse(schemaKlassTypesField);

        primitiveKlass.fields(primitiveKlassNameField, primitiveKlassSchemaField);

        // ========================
        // * Klass Klass
        final Klass klassKlass = new KlassImpl("Klass");
        klassKlass.schema(schemaSchema);

        klassKlass.supers(typeKlass);
        typeKlass.subklasses(primitiveKlass, klassKlass);

        final Field klassKlassNameField = new FieldImpl("name", false, false);
        // klassKlassNameField.key(true);
        klassKlassNameField.owner(klassKlass);
        klassKlassNameField.type(stringPrimitive);

        final Field klassKlassSupersField = new FieldImpl("supers", true, false);
        klassKlassSupersField.owner(klassKlass);
        klassKlassSupersField.type(klassKlass);

        final Field klassKlassSubsField = new FieldImpl("subklasses", true, false);
        klassKlassSubsField.owner(klassKlass);
        klassKlassSubsField.type(klassKlass);
        klassKlassSubsField.inverse(klassKlassSupersField);

        final Field klassKlassFieldsField = new FieldImpl("fields", true, false);
        klassKlassFieldsField.owner(klassKlass);

        final Field klassKlassSchemaField = new FieldImpl("schema", false, false);
        klassKlassSchemaField.owner(klassKlass);
        klassKlassSchemaField.type(schemaKlass);
        klassKlassSchemaField.inverse(schemaKlassTypesField);

        // TODO: add classOf()

        klassKlass.fields(
            klassKlassNameField,
            klassKlassSupersField,
            klassKlassSubsField,
            klassKlassFieldsField,
            klassKlassSchemaField
        );

        // ========================
        // * Field Klass
        final Klass fieldKlass = new KlassImpl("Field");
        klassKlass.schema(schemaSchema);

        klassKlassFieldsField.type(fieldKlass);

        final Field fieldKlassNameField = new FieldImpl("name", false, false);
        // fieldKlassNameField.key(true);
        fieldKlassNameField.owner(fieldKlass);
        fieldKlassNameField.type(stringPrimitive);

        final Field fieldKlassOwnerField = new FieldImpl("owner", false, false);
        fieldKlassOwnerField.owner(fieldKlass);
        fieldKlassOwnerField.type(klassKlass);

        final Field fieldKlassTypeField = new FieldImpl("type", false, false);
        fieldKlassTypeField.owner(fieldKlass);
        fieldKlassTypeField.type(typeKlass);

        final Field fieldKlassManyField = new FieldImpl("many", false, false);
        fieldKlassManyField.owner(fieldKlass);
        fieldKlassManyField.type(booleanPrimitive);

        final Field fieldKlassOptionalField = new FieldImpl("optional", false, false);
        fieldKlassOptionalField.owner(fieldKlass);
        fieldKlassOptionalField.type(booleanPrimitive);

        final Field fieldKlassInverseField = new FieldImpl("inverse", false, true);
        fieldKlassInverseField.owner(fieldKlass);
        fieldKlassInverseField.type(fieldKlass);
        fieldKlassInverseField.inverse(fieldKlassInverseField);

        // TODO:
//        final Field fieldKlassKeyField = new FieldImpl("key", false, false);
//        fieldKlassKeyField.owner(fieldKlass);
//        fieldKlassKeyField.type(booleanPrimitive);

//        final Field fieldKlassContentField = new FieldImpl("content", false, false);
//        fieldKlassContentField.owner(fieldKlass);
//        fieldKlassContentField.type(booleanPrimitive);

        fieldKlass.fields(
            fieldKlassNameField,
            fieldKlassOwnerField,
            fieldKlassTypeField,
            fieldKlassManyField,
            fieldKlassOptionalField,
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

        this.klassInterfaces = new HashSet<>(Arrays.asList(
                Schema.class,
                Type.class,
                Klass.class,
                Primitive.class,
                Field.class
        ));
    }
}
