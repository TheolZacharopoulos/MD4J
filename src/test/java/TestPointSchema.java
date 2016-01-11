import nl.cwi.examples.geometry.Line;
import nl.cwi.examples.geometry.Point;
import nl.cwi.examples.geometry.PointFactory;
import nl.cwi.managed_data_4j.data_managers.BasicFactory;
import nl.cwi.managed_data_4j.data_managers.DataManagerFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.boot.boot_primitives.IntegerPrimitive;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.load.models.LoadKlass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Field;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Klass;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Schema;
import nl.cwi.managed_data_4j.schema.models.schema_schema.Type;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;
import static org.hamcrest.Matchers.is;

public class TestPointSchema {

    Schema bootstrapSchema;
    BasicFactory basicFactory;
    SchemaFactory schemaFactory;
    Schema pointSchema;
    PointFactory pointFactory;

    @Before
    public void setup() {
        bootstrapSchema = SchemaLoader.bootLoad();

        basicFactory = new BasicFactory();
        schemaFactory = DataManagerFactory.make(basicFactory, SchemaFactory.class, bootstrapSchema);
        pointSchema = SchemaLoader.load(schemaFactory, Point.class, Line.class);
        pointFactory = DataManagerFactory.make(basicFactory, PointFactory.class, pointSchema);
    }

    @Test
    public void testSchema() {
        assertThat(pointSchema.klassInterfaces(), hasItems(
                Point.class,
                Line.class
        ));

        assertThat(pointSchema.types().size(), is(2));
        assertThat(pointSchema.klasses().size(), is(2));

        Klass pointType = null;
        Klass lineType = null;

        for (Type type : pointSchema.types()) {
            if (type.name().equals("Point"))
                pointType = (Klass) type;

            if (type.name().equals("Line"))
                lineType = (Klass) type;
        }

        assertNotNull(pointType);
        assertEquals(pointType.name(), "Point");


        assertNotNull(lineType);
        assertEquals(lineType.name(), "Line");
    }

    @Test
    public void testPointSchema() {
        Klass pointType = null;

        for (Type type : pointSchema.types()) {
            if (type.name().equals("Point"))
                pointType = (Klass) type;
        }

        assertNotNull(pointType);

        Field xField = null;
        Field yField = null;
        for (Field field : pointType.fields()) {
            if (field.name().equals("x"))
               xField = field;

            if (field.name().equals("y"))
                yField = field;
        }

        assertNotNull(xField);
        assertThat(xField.name(), is("x"));
        assertThat(xField.optional(), is(false));
        assertThat(xField.many(), is(false));
        assertThat(xField.owner(), is(pointType));
        assertThat(xField.type(), is(new IntegerPrimitive(pointSchema)));

        assertNotNull(yField);
        assertThat(yField.name(), is("y"));
        assertThat(yField.optional(), is(false));
        assertThat(yField.many(), is(false));
        assertThat(yField.owner(), is(pointType));
        assertThat(yField.type(), is(new IntegerPrimitive(pointSchema)));
    }

    @Test
    public void testLineSchema() {
        Klass lineType = null;

        for (Type type : pointSchema.types()) {
            if (type.name().equals("Line"))
                lineType = (Klass) type;
        }

        assertNotNull(lineType);

        Field startPointField = null;
        Field endPointField = null;
        for (Field field : lineType.fields()) {
            if (field.name().equals("startPoint"))
                startPointField = field;

            if (field.name().equals("endPoint"))
                endPointField = field;
        }

        assertNotNull(startPointField);
        assertThat(startPointField.name(), is("startPoint"));
        assertThat(startPointField.optional(), is(false));
        assertThat(startPointField.many(), is(false));
        assertThat(startPointField.owner(), is(lineType));
        assertThat(startPointField.type(), is(
                new LoadKlass(
                    "Point",
                    pointSchema,
                    Collections.emptySet(),
                    Collections.emptySet(),
                    new HashSet<>(Arrays.asList(startPointField, endPointField))))
        );

        assertNotNull(endPointField);
        assertThat(endPointField.name(), is("endPoint"));
        assertThat(endPointField.optional(), is(false));
        assertThat(endPointField.many(), is(false));
        assertThat(endPointField.owner(), is(lineType));
        assertThat(endPointField.type(), is(
                new LoadKlass(
                    "Point",
                    pointSchema,
                    Collections.emptySet(),
                    Collections.emptySet(),
                    new HashSet<>(Arrays.asList(startPointField, endPointField))))
        );
    }

    @Test
    public void testEmptyIntegerInitialization() {
        Point point = pointFactory.point();
        assertThat(point.x(), is(0));
        assertThat(point.y(), is(0));
    }

    @Test
    public void testNonEmptyIntegerInitialization() {
        Integer x = 1;
        Integer y = 2;

        Point point = pointFactory.point(x, y);
        assertThat(point.x(), is(x));
        assertThat(point.y(), is(y));
    }
}
