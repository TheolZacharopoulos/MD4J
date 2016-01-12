import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.data_manager.DataManagerFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import org.junit.Before;
import org.junit.Test;
import utils.TestHelper;


import java.util.Set;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TestSchemaSchema {

    Schema bootstrapSchema;
    BasicFactory basicFactory;
    SchemaFactory schemaFactory;
    Schema realSchemaSchema;

    @Before
    public void setup() {
        bootstrapSchema = SchemaLoader.bootLoad();

        basicFactory = new BasicFactory();
        schemaFactory = DataManagerFactory.make(basicFactory, SchemaFactory.class, bootstrapSchema);
        realSchemaSchema = SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
    }

    @Test
    public void testBootStrap_schemaSchema() {
        final Set<Class> bootstrapSchemaKlassInterfaces = bootstrapSchema.klassInterfaces();
        final Set<Class> realSchemaSchemaKlassInterfaces = realSchemaSchema.klassInterfaces();

        assertThat(bootstrapSchemaKlassInterfaces, is(realSchemaSchemaKlassInterfaces));

        final Set<Type> bootstrapSchemaTypes = bootstrapSchema.types();
        final Set<Type> realSchemaSchemaTypes = realSchemaSchema.types();

        // Schema Klass
        final Klass bootstrapSchemaKlass = TestHelper.getKlass(bootstrapSchemaTypes, "Schema");
        final Klass realSchemaSchemaKlass = TestHelper.getKlass(realSchemaSchemaTypes, "Schema");

        assertNotNull(bootstrapSchemaKlass);
        assertNotNull(realSchemaSchemaKlass);

        assertEquals(bootstrapSchemaKlass.name(), realSchemaSchemaKlass.name());
        assertEquals(bootstrapSchemaKlass.supers(), realSchemaSchemaKlass.supers());
        assertEquals(bootstrapSchemaKlass.subklasses(), realSchemaSchemaKlass.subklasses());
        assertEquals(bootstrapSchemaKlass.fields(), realSchemaSchemaKlass.fields());

        // Type Klass
        final Klass bootstrapTypeKlass = TestHelper.getKlass(bootstrapSchemaTypes, "Type");
        final Klass realSchemaTypeKlass = TestHelper.getKlass(realSchemaSchemaTypes, "Type");

        assertNotNull(bootstrapTypeKlass);
        assertNotNull(realSchemaTypeKlass);

        assertEquals(bootstrapTypeKlass.name(), realSchemaTypeKlass.name());
        assertEquals(bootstrapTypeKlass.supers(), realSchemaTypeKlass.supers());
//        assertEquals(bootstrapTypeKlass.subklasses(), realSchemaTypeKlass.subklasses());
        assertEquals(bootstrapTypeKlass.fields(), realSchemaTypeKlass.fields());

        // Primitive Klass
        final Klass bootstrapPrimitiveKlass = TestHelper.getKlass(bootstrapSchemaTypes, "Primitive");
        final Klass realSchemaPrimitiveKlass = TestHelper.getKlass(realSchemaSchemaTypes, "Primitive");

        assertNotNull(bootstrapPrimitiveKlass);
        assertNotNull(realSchemaPrimitiveKlass);

        assertEquals(bootstrapPrimitiveKlass.name(), realSchemaPrimitiveKlass.name());
//        assertEquals(bootstrapPrimitiveKlass.supers(), realSchemaPrimitiveKlass.supers());
//        assertEquals(bootstrapPrimitiveKlass.subklasses(), realSchemaPrimitiveKlass.subklasses());
        assertEquals(bootstrapPrimitiveKlass.fields(), realSchemaPrimitiveKlass.fields());

        // Klass Klass
        final Klass bootstrapKlassKlass = TestHelper.getKlass(bootstrapSchemaTypes, "Klass");
        final Klass realSchemaKlassKlass = TestHelper.getKlass(realSchemaSchemaTypes, "Klass");

        assertNotNull(bootstrapKlassKlass);
        assertNotNull(realSchemaKlassKlass);

        assertEquals(bootstrapKlassKlass.name(), realSchemaKlassKlass.name());
//        assertEquals(bootstrapKlassKlass.supers(), realSchemaKlassKlass.supers());
//        assertEquals(bootstrapKlassKlass.subklasses(), realSchemaKlassKlass.subklasses());
        assertEquals(bootstrapKlassKlass.fields(), realSchemaKlassKlass.fields());

        // Field Klass
        final Klass bootstrapFieldKlass = TestHelper.getKlass(bootstrapSchemaTypes, "Field");
        final Klass realSchemaFieldKlass = TestHelper.getKlass(realSchemaSchemaTypes, "Field");

        assertNotNull(bootstrapFieldKlass);
        assertNotNull(realSchemaFieldKlass);

        assertEquals(bootstrapFieldKlass.name(), realSchemaFieldKlass.name());
//        assertEquals(bootstrapFieldKlass.supers(), realSchemaFieldKlass.supers());
//        assertEquals(bootstrapFieldKlass.subklasses(), realSchemaFieldKlass.subklasses());
        assertEquals(bootstrapFieldKlass.fields(), realSchemaFieldKlass.fields());
    }
}
