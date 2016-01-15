import nl.cwi.examples.geometry.Line;
import nl.cwi.examples.geometry.Point;
import nl.cwi.examples.geometry.PointFactory;
import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Schema;
import nl.cwi.managed_data_4j.schema.models.implementation.PrimitiveImpl;
import org.junit.Before;
import org.junit.Test;
import utils.TestHelper;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class TestPointSchema {

    Schema bootstrapSchema;
    BasicFactory basicFactorySchemaSchema;
    BasicFactory basicFactoryPointSchema;
    SchemaFactory schemaFactory;
    Schema pointSchema;
    PointFactory pointFactory;

    @Before
    public void setup() {
        bootstrapSchema = SchemaLoader.bootLoad();

        basicFactorySchemaSchema = new BasicFactory(SchemaFactory.class, bootstrapSchema);
        schemaFactory = basicFactorySchemaSchema.make();

        pointSchema = SchemaLoader.load(schemaFactory, Point.class, Line.class);
        basicFactoryPointSchema = new BasicFactory(PointFactory.class, pointSchema);
    }

    @Test
    public void testSchema() {
//        assertThat(pointSchema.klassInterfaces(), hasItems(
//                Point.class,
//                Line.class
//        ));

        assertThat(pointSchema.types().size(), is(2));
        assertThat(pointSchema.klasses().size(), is(2));

        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point");
        Klass lineType = TestHelper.getKlass(pointSchema.types(), "Line");

        assertNotNull(pointType);
        assertEquals(pointType.name(), "Point");

        assertNotNull(lineType);
        assertEquals(lineType.name(), "Line");
    }

    @Test
    public void testPointSchema() {
        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point");

        Field xField = TestHelper.getField(pointType.fields(), "x");
        Field yField = TestHelper.getField(pointType.fields(), "y");

        assertNotNull(xField);
        assertThat(xField.name(), is("x"));
        assertThat(xField.optional(), is(false));
        assertThat(xField.many(), is(false));
        assertThat(xField.owner(), is(pointType));
        assertThat(xField.type(), is(new PrimitiveImpl("Integer", pointSchema)));

        assertNotNull(yField);
        assertThat(yField.name(), is("y"));
        assertThat(yField.optional(), is(false));
        assertThat(yField.many(), is(false));
        assertThat(yField.owner(), is(pointType));
        assertThat(yField.type(), is(new PrimitiveImpl("Integer", pointSchema)));
    }

    @Test
    public void testLineSchema() {
        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point");
        Field xField = TestHelper.getField(pointType.fields(), "x");
        Field yField = TestHelper.getField(pointType.fields(), "y");

        Klass lineType = TestHelper.getKlass(pointSchema.types(), "Line");
        Field startPointField = TestHelper.getField(lineType.fields(), "startPoint");
        Field endPointField = TestHelper.getField(lineType.fields(), "endPoint");

        assertNotNull(lineType);

        assertNotNull(startPointField);
        assertThat(startPointField.name(), is("startPoint"));
        assertThat(startPointField.optional(), is(false));
        assertThat(startPointField.many(), is(false));
        assertThat(startPointField.owner(), is(lineType));
//        assertThat(startPointField.type(), is(
//                new KlassImpl(
//                    "Point",
//                    pointSchema,
//                    new LinkedHashSet<>(Arrays.asList(xField, yField)),
//                    Collections.emptySet(),
//                    Collections.emptySet()))
//        );

        assertNotNull(endPointField);
        assertThat(endPointField.name(), is("endPoint"));
        assertThat(endPointField.optional(), is(false));
        assertThat(endPointField.many(), is(false));
        assertThat(endPointField.owner(), is(lineType));
//        assertThat(endPointField.type(), is(
//                new KlassImpl(
//                        "Point",
//                        pointSchema,
//                        new LinkedHashSet<>(Arrays.asList(xField, yField)),
//                        Collections.emptySet(),
//                        Collections.emptySet()))
//        );
    }

    @Test
    public void testEmptyPointInitialization() {
        Point point = pointFactory.point();
        assertThat(point.x(), is(0));
        assertThat(point.y(), is(0));
    }

    @Test
    public void testNonEmptyPointInitialization() {
        final Integer x = 1;
        final Integer y = 2;

        Point point = pointFactory.point(x, y);
        assertEquals(point.x(), x);
        assertEquals(point.y(), y);
    }

    @Test
    public void testEmptyLineInitialization() {
        Line line = pointFactory.line();
        assertNull(line.startPoint());
        assertNull(line.endPoint());
    }

    @Test(expected=RuntimeException.class)
    public void testNonEmptyLineInitialization_nonMObject() {
        final Point startPoint = new Point() {
            @Override
            public Integer x(Integer... x) {
                return 1;
            }

            @Override
            public Integer y(Integer... y) {
                return 2;
            }
        };

        final Point endPoint = new Point() {
            @Override
            public Integer x(Integer... x) {
                return 3;
            }

            @Override
            public Integer y(Integer... y) {
                return 4;
            }
        };

        Line line = pointFactory.line(startPoint, endPoint);
        assertEquals(line.startPoint().x(), new Integer(1));
        assertEquals(line.startPoint().y(), new Integer(2));
        assertEquals(line.endPoint().x(), new Integer(3));
        assertEquals(line.endPoint().y(), new Integer(4));
    }

    @Test
    public void testNonEmptyLineInitialization() {
        final Point startPoint = pointFactory.point(1, 2);

        final Point endPoint = pointFactory.point(3, 4);

        Line line = pointFactory.line(startPoint, endPoint);
        assertEquals(line.startPoint().x(), new Integer(1));
        assertEquals(line.startPoint().y(), new Integer(2));
        assertEquals(line.endPoint().x(), new Integer(3));
        assertEquals(line.endPoint().y(), new Integer(4));
    }
}
