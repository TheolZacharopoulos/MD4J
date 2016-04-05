//import nl.cwi.examples.geometry.PointFactory;
//import nl.cwi.examples.geometry.schemas.Line;
//import nl.cwi.examples.geometry.schemas.Point;
//import nl.cwi.examples.geometry.schemas.Point2D;
//import nl.cwi.examples.geometry.schemas.Point3D;
//import nl.cwi.managed_data_4j.language.data_manager.BasicFactory;
//import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
//import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
//import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
//import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
//import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;
//import org.junit.Before;
//import org.junit.Test;
//import utils.TestHelper;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.*;
//
//public class TestPointSchema {
//
//    Schema bootstrapSchema;
//    BasicFactory basicFactorySchemaSchema;
//    BasicFactory basicFactoryPointSchema;
//    SchemaFactory schemaFactory;
//    Schema pointSchema;
//    PointFactory pointFactory;
//
//    @Before
//    public void setup() {
//        bootstrapSchema = SchemaLoader.bootLoad();
//
//        basicFactorySchemaSchema = new BasicFactory(SchemaFactory.class, bootstrapSchema);
//        schemaFactory = basicFactorySchemaSchema.make();
//
//        pointSchema = SchemaLoader.load(schemaFactory, bootstrapSchema, Point.class, Point2D.class, Point3D.class, Line.class);
//        basicFactoryPointSchema = new BasicFactory(PointFactory.class, pointSchema);
//        pointFactory = basicFactoryPointSchema.make();
//    }
//
//    @Test
//    public void testSchema() {
//        assertThat(pointSchema.types().size(), is(4));
//        assertThat(pointSchema.klasses().size(), is(4));
//
//        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point2D");
//        Klass lineType = TestHelper.getKlass(pointSchema.types(), "Line");
//
//        assertNotNull(pointType);
//        assertEquals(pointType.name(), "Point2D");
//
//        assertNotNull(lineType);
//        assertEquals(lineType.name(), "Line");
//    }
//
//    @Test
//    public void testPointSchema() {
//        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point2D");
//
//        Field xField = TestHelper.getField(pointType.fields(), "x");
//        Field yField = TestHelper.getField(pointType.fields(), "y");
//
//        assertNotNull(xField);
//        assertThat(xField.name(), is("x"));
//        assertThat(xField.optional(), is(false));
//        assertThat(xField.many(), is(false));
//        assertThat(xField.owner().name(), is("Point2D"));
//        assertThat(xField.type().name(), is("Integer"));
//
//        assertNotNull(yField);
//        assertThat(yField.name(), is("y"));
//        assertThat(yField.optional(), is(false));
//        assertThat(yField.many(), is(false));
//        assertThat(yField.owner().name(), is("Point2D"));
//        assertThat(yField.type().name(), is("Integer"));
//    }
//
//    @Test
//    public void testLineSchema() {
//        Klass pointType = TestHelper.getKlass(pointSchema.types(), "Point2D");
//        Field xField = TestHelper.getField(pointType.fields(), "x");
//        Field yField = TestHelper.getField(pointType.fields(), "y");
//
//        Klass lineType = TestHelper.getKlass(pointSchema.types(), "Line");
//        Field startPointField = TestHelper.getField(lineType.fields(), "startPoint");
//        Field endPointField = TestHelper.getField(lineType.fields(), "endPoint");
//
//        assertNotNull(lineType);
//
//        assertNotNull(startPointField);
//        assertThat(startPointField.name(), is("startPoint"));
//        assertThat(startPointField.optional(), is(false));
//        assertThat(startPointField.many(), is(false));
//        assertThat(startPointField.owner().name(), is("Line"));
//
//
//        assertNotNull(endPointField);
//        assertThat(endPointField.name(), is("endPoint"));
//        assertThat(endPointField.optional(), is(false));
//        assertThat(endPointField.many(), is(false));
//        assertThat(endPointField.owner().name(), is("Line"));
//    }
//
//    @Test
//    public void testEmptyPointInitialization() {
//        Point2D point2D = pointFactory.point2D();
//        assertThat(point2D.x(), is(0));
//        assertThat(point2D.y(), is(0));
//    }
//
//    @Test
//    public void testNonEmptyPointInitialization() {
//        final Integer x = 1;
//        final Integer y = 2;
//
//        Point2D point2D = pointFactory.point2D(x, y);
//        assertEquals(point2D.x(), x);
//        assertEquals(point2D.y(), y);
//    }
//
//    @Test
//    public void testEmptyLineInitialization() {
//        Line line = pointFactory.line();
//        assertNull(line.startPoint());
//        assertNull(line.endPoint());
//    }
//
//    @Test(expected=RuntimeException.class)
//    public void testNonEmptyLineInitialization_nonMObject() {
//        final Point2D startPoint2D = new Point2D() {
//            @Override
//            public Integer x(Integer... x) {
//                return 1;
//            }
//
//            @Override
//            public Integer y(Integer... y) {
//                return 2;
//            }
//        };
//
//        final Point2D endPoint2D = new Point2D() {
//            @Override
//            public Integer x(Integer... x) {
//                return 3;
//            }
//
//            @Override
//            public Integer y(Integer... y) {
//                return 4;
//            }
//        };
//
//        Line line = pointFactory.line();
//        line.startPoint(startPoint2D);
//        line.endPoint(endPoint2D);
//        assertEquals(line.startPoint().x(), new Integer(1));
//        assertEquals(line.startPoint().y(), new Integer(2));
//        assertEquals(line.endPoint().x(), new Integer(3));
//        assertEquals(line.endPoint().y(), new Integer(4));
//    }
//
//    @Test
//    public void testNonEmptyLineInitialization() {
//        final Point2D startPoint2D = pointFactory.point2D(1, 2);
//
//        final Point2D endPoint2D = pointFactory.point2D(3, 4);
//
//        Line line = pointFactory.line();
//        line.startPoint(startPoint2D);
//        line.endPoint(endPoint2D);
//        assertEquals(line.startPoint().x(), new Integer(1));
//        assertEquals(line.startPoint().y(), new Integer(2));
//        assertEquals(line.endPoint().x(), new Integer(3));
//        assertEquals(line.endPoint().y(), new Integer(4));
//    }
//}
