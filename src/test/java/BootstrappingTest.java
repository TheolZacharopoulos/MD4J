import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import org.junit.Before;
import org.junit.Test;

public class BootstrappingTest {

    Schema bootstrapSchema;
    Schema realSchemaSchema;

    @Before
    public void setup() {
        bootstrapSchema = SchemaLoader.bootLoad();
        BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);
        SchemaFactory schemaFactory = basicFactory.make();

        realSchemaSchema = SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
    }

    @Test
    public void testEqualTypes() {
        // TODO
    }
}
