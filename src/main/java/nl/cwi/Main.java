package nl.cwi;

import nl.cwi.examples.ccc.UpdateLogger;
import nl.cwi.examples.geometry.*;
import nl.cwi.examples.patterns.lockable.Lockable;
import nl.cwi.examples.patterns.lockable.LockableFactory;
import nl.cwi.examples.patterns.observer.Observable;
import nl.cwi.examples.patterns.observer.ObservableFactory;
import nl.cwi.managed_data_4j.data_manager.BasicFactory;
import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.schema.models.definition.*;
import nl.cwi.managed_data_4j.utils.MObjectUtils;

public class Main {

    public static void main(String[] args) {

        // ================================ Schema Schema ========================================

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        final Schema bootstrapSchema = SchemaLoader.bootLoad();

        final BasicFactory basicFactory = new BasicFactory(SchemaFactory.class, bootstrapSchema);

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.make();

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema =
                SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);

//        // Test equality
//        final BasicFactory basicFactory2 = new BasicFactory(SchemaFactory.class, realSchemaSchema);
//        final SchemaFactory schemaFactory2 = basicFactory2.make();
//        final Schema realSchemaSchema2 =
//            SchemaLoader.load(schemaFactory2, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
//        boolean equal = MObjectUtils.isEqual(realSchemaSchema, realSchemaSchema2);

        // ================================ Data objects ========================================
        System.out.println("=============");
        System.out.println("Data Objects: ");

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting props.
        final Schema pointSchema = SchemaLoader.load(schemaFactory, Point.class, Point2D.class, Point3D.class, Line.class);
        final BasicFactory basicFactoryForPoints = new BasicFactory(PointFactory.class, pointSchema);
        final PointFactory pointFactory = basicFactoryForPoints.make();

        final Point2D point = pointFactory.point2D(3, 2);

//        // Test equality
//        final Point2D point2 = pointFactory.point2D(3, 2);
//        boolean equalPoints = MObjectUtils.isEqual(point, point2);

        final Line line = pointFactory.line();
        line.startPoint(point);
        line.endPoint(point);

//        // Test equality
//        final Line line2 = pointFactory.line();
//        line2.startPoint(point2);
//        line2.endPoint(point2);
//        boolean equalLines = MObjectUtils.isEqual(line, line2);

        System.out.print(point.x() + " + " + point.y() + " = ");
        System.out.print(point.x() + point.y());
        System.out.print(" == ");
        System.out.println(line.startPoint().x() + line.startPoint().y());

        // ================================ Observer ========================================
        System.out.println("=============");
        System.out.println("Observable Objects: ");

        final ObservableFactory observableFactory = new ObservableFactory(PointFactory.class, pointSchema);
        final PointFactory observablePointFactory = observableFactory.make();

        // Create a new observer-record managed object.
        final Point3D observerPoint = observablePointFactory.point3D();

        // Add Logging concerns
        ((Observable) observerPoint).observe(UpdateLogger::log);

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.z(9);
        observerPoint.x(observerPoint.x() + observerPoint.y());
        System.out.println(observerPoint.x() + observerPoint.y() + observerPoint.z());

        // ================================ Lockable ========================================
        System.out.println("=============");
        System.out.println("Lockable Objects: ");

        final LockableFactory lockableFactory = new LockableFactory(PointFactory.class, pointSchema);
        final PointFactory lockablePointFactory = lockableFactory.make();
        final Point2D lockablePoint = lockablePointFactory.point2D();
        lockablePoint.x(1);
        lockablePoint.y(1);

        // It was mutable until now, now it is locked (immutable).
        ((Lockable)lockablePoint).lock();

        try {
            lockablePoint.x(2); // Should throw here since its immutable.
        } catch (IllegalAccessError e) {
            System.out.println(e.getMessage());
        }
        System.out.println(lockablePoint.x());
    }
}
