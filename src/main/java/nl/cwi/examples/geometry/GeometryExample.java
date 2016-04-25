package nl.cwi.examples.geometry;

import nl.cwi.examples.geometry.schemas.Line;
import nl.cwi.examples.geometry.schemas.Point;
import nl.cwi.examples.geometry.schemas.Point2D;
import nl.cwi.examples.geometry.schemas.Point3D;
import nl.cwi.managed_data_4j.ccconcerns.aspects.UpdateLogger;
import nl.cwi.managed_data_4j.ccconcerns.patterns.lockable.Lockable;
import nl.cwi.managed_data_4j.ccconcerns.patterns.lockable.LockableDataManager;
import nl.cwi.managed_data_4j.ccconcerns.patterns.observer.Observable;
import nl.cwi.managed_data_4j.ccconcerns.patterns.observer.ObservableDataManager;
import nl.cwi.managed_data_4j.framework.SchemaFactoryProvider;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.Schema;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class GeometryExample {

    public static void main(String[] args) {
        final Schema schemaSchema = SchemaFactoryProvider.getSchemaSchema();
        final SchemaFactory schemaFactory = SchemaFactoryProvider.getSchemaFactory();

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting props.
        // use the schemaFactory2, the schema factory which has been made from the realSchemaSchema
        final Schema pointSchema = SchemaLoader.load(
                schemaFactory, schemaSchema, Point.class, Point2D.class, Point3D.class, Line.class, Primitives.class);
        final BasicDataManager basicFactoryForPoints = new BasicDataManager(PointFactory.class, pointSchema);
        final PointFactory pointFactory = basicFactoryForPoints.make();

        final Point2D point = pointFactory.Point2D(3, 2);

        final Line line = pointFactory.Line();
        line.startPoint(point);
        line.endPoint(point);

        System.out.print(point.x() + " + " + point.y() + " = ");
        System.out.print(point.x() + point.y());
        System.out.print(" == ");
        System.out.println(line.startPoint().x() + line.startPoint().y());

        // Check multi points
        Set<Point2D> points = new LinkedHashSet<>(Arrays.asList(
            pointFactory.Point2D(1, 2),
            pointFactory.Point2D(2, 3),
            pointFactory.Point2D(1, 4)));

        System.out.println("============");
        System.out.println("Set of 2D points: ");
        for (Point2D point2D : points) {
            System.out.println("x = " + point2D.x() + ", y = " + point2D.y());
        }

        // ================================ Observer ========================================
        System.out.println("=============");
        System.out.println("Observable Objects: ");

        final ObservableDataManager observableFactory = new ObservableDataManager(PointFactory.class, pointSchema);
        final PointFactory observablePointFactory = observableFactory.make();

        // Create a new observer-record managed object.
        final Point3D observerPoint = observablePointFactory.Point3D();

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

        final LockableDataManager lockableFactory = new LockableDataManager(PointFactory.class, pointSchema);
        final PointFactory lockablePointFactory = lockableFactory.make();
        final Point2D lockablePoint = lockablePointFactory.Point2D();
        lockablePoint.x(1);
        lockablePoint.y(1);

        // It was mutable until now, now it is locked (immutable).
        ((Lockable)lockablePoint).lock();

        try {
            lockablePoint.x(2); // Should throw here since its immutable.
        } catch (IllegalAccessError e) {
            System.out.println("IllegalAccessError: " + e.getMessage());
        }
        System.out.println(lockablePoint.x());

        System.exit(0);
    }
}