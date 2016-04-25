import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TestBootstrap {

    @Before
    public void setup() {
    }

    @Test
    public void equality_Test() {
        final Schema bootstrapSchema = new BootSchema();

        final BasicDataManager basicFactory = new BasicDataManager(SchemaFactory.class, bootstrapSchema);

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.make();

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema = SchemaLoader.load(
                schemaFactory, bootstrapSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        // =======================
        // Test equality
        final BasicDataManager basicFactory2 = new BasicDataManager(SchemaFactory.class, realSchemaSchema);
        final SchemaFactory schemaFactory2 = basicFactory2.make();
        final Schema realSchemaSchema2 = SchemaLoader.load(
                schemaFactory2, realSchemaSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        assertTrue(MObjectUtils.equals(bootstrapSchema, realSchemaSchema));
        assertTrue(MObjectUtils.equals(realSchemaSchema, realSchemaSchema2));
        assertTrue(MObjectUtils.equals(bootstrapSchema, realSchemaSchema2));
    }
}