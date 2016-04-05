import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import org.junit.Before;
import org.junit.Test;
import utils.TestHelper;

public class BootstrappingTest {

    Schema bootstrapSchema;
    Schema realSchemaSchema;
    Schema realSchemaSchema2;

    @Before
    public void setup() {
        bootstrapSchema = SchemaLoader.bootLoad();
        BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);
        SchemaFactory schemaFactory = basicFactory.make();

        realSchemaSchema = SchemaLoader.load(schemaFactory, bootstrapSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

        BasicFactory basicFactory2 = new BasicFactory(SchemaFactory.class, realSchemaSchema);
        SchemaFactory schemaFactory2 = basicFactory2.make();
        realSchemaSchema2 = SchemaLoader.load(schemaFactory2, bootstrapSchema, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
    }

    @Test
    public void testEqualKlass() {
        Klass bootstrapKlass = TestHelper.getKlass(bootstrapSchema.types(), "Klass");
        Klass realSchemaSchemaKlass = TestHelper.getKlass(realSchemaSchema.types(), "Klass");
        Klass realSchemaSchema2Klass = TestHelper.getKlass(realSchemaSchema2.types(), "Klass");

        assert(bootstrapKlass.classOf().equals(realSchemaSchemaKlass.classOf()));
        assert(realSchemaSchemaKlass.classOf().equals(realSchemaSchema2Klass.classOf()));

        assert(bootstrapKlass.supers().size() == realSchemaSchemaKlass.supers().size());
        assert(realSchemaSchemaKlass.supers().size() == realSchemaSchema2Klass.supers().size());

    }
}
