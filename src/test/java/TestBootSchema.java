import nl.cwi.managed_data_4j.schema.boot.BootSchema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.*;

public class TestBootSchema {

    Schema bootSchema;

    @Before
    public void setup() {
        bootSchema = new BootSchema();
    }

    @Test
    public void testBootStrapClass_klassInterfaces() {
        Set<Class> klassInterfaces = bootSchema.klassInterfaces();
        assertThat(klassInterfaces, hasItems(
                Schema.class,
                Type.class,
                Klass.class,
                Primitive.class,
                Field.class)
        );
    }

    @Test
    @Ignore("TODO")
    public void testBootStrapClass_types() {

    }
}
