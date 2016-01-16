package nl.cwi;

import nl.cwi.examples.ccc.UpdateLogger;
import nl.cwi.examples.geometry.*;
import nl.cwi.examples.patterns.observer.Observable;
import nl.cwi.examples.patterns.observer.ObservableFactory;
import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.utils.DebugUtils;

public class Main {

    public static void main(String[] args) {

        // ================================ Schema Schema ========================================

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        Schema bootstrapSchema = SchemaLoader.bootLoad();

        final BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.make();

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        Schema realSchemaSchema =
                SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
//        DebugUtils.debugTypes(realSchemaSchema.types());

        // TODO: This Should work also.
//        final BasicFactory basicFactory2 = new BasicFactory(SchemaFactory.class, realSchemaSchema);
//        SchemaFactory schemaFactory2 = basicFactory2.make();
//        Schema realSchemaSchema2 =
//            SchemaLoader.load(schemaFactory2, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

        // ================================ Data objects ========================================

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting props.
        Schema pointSchema = SchemaLoader.load(schemaFactory, Point.class, Point2D.class, Point3D.class, Line.class);
        BasicFactory basicFactoryForPoints = new BasicFactory(PointFactory.class, pointSchema);
        PointFactory pointFactory = basicFactoryForPoints.make();
        Point2D point = pointFactory.point2D(3, 2);
        Line line = pointFactory.line(point, point);

        System.out.print(point.x() + " + " + point.y() + " = ");
        System.out.print(point.x() + point.y());
        System.out.print(" == ");
        System.out.println(line.startPoint().x() + line.startPoint().y());

        // ================================ Observer ========================================
        final ObservableFactory observableFactory = new ObservableFactory(PointFactory.class, pointSchema);
        PointFactory observablePointFactory = observableFactory.make();

        // Create a new observer-record managed object.
        Point3D observerPoint = observablePointFactory.point3D();

        // Add Logging concerns
        ((Observable) observerPoint).observe(UpdateLogger::log);

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.z(9);
        observerPoint.x(observerPoint.x() + observerPoint.y());
        System.out.println(observerPoint.x() + observerPoint.y() + observerPoint.z());
    }
}
