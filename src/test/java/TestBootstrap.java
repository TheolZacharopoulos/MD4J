import nl.cwi.java_managed_data.language.data_manager.BasicDataManager;
import nl.cwi.java_managed_data.language.primitives.Primitives;
import nl.cwi.java_managed_data.language.schema.boot.BootSchema;
import nl.cwi.java_managed_data.language.schema.boot.SchemaFactory;
import nl.cwi.java_managed_data.language.schema.load.SchemaLoader;
import nl.cwi.java_managed_data.language.schema.models.definition.*;
import nl.cwi.java_managed_data.language.utils.MObjectUtils;
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

        final BasicDataManager basicFactory = new BasicDataManager();

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.factory(SchemaFactory.class, bootstrapSchema);

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema = SchemaLoader.load(
                schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        // =======================
        // Test equality
        final BasicDataManager basicFactory2 = new BasicDataManager();
        final SchemaFactory schemaFactory2 = basicFactory2.factory(SchemaFactory.class, realSchemaSchema);
        final Schema realSchemaSchema2 = SchemaLoader.load(
                schemaFactory2, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        assertTrue(MObjectUtils.equals(bootstrapSchema, realSchemaSchema));
        assertTrue(MObjectUtils.equals(realSchemaSchema, realSchemaSchema2));
        assertTrue(MObjectUtils.equals(bootstrapSchema, realSchemaSchema2));
    }
}