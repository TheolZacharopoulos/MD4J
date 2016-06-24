package nl.cwi.examples.geometry;

import nl.cwi.examples.geometry.schemas.Line;
import nl.cwi.examples.geometry.schemas.Point;
import nl.cwi.examples.geometry.schemas.Point2D;
import nl.cwi.examples.geometry.schemas.Point3D;
import nl.cwi.managed_data_4j.language.data_manager.BasicDataManager;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.managed_data_4j.language.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.language.schema.load.SchemaLoader;
import nl.cwi.managed_data_4j.language.schema.models.definition.*;
import nl.cwi.managed_data_4j.language.utils.DebugUtils;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import nl.cwi.examples.ccconcerns.aspects.UpdateLogger;
import nl.cwi.examples.ccconcerns.patterns.lockable.Lockable;
import nl.cwi.examples.ccconcerns.patterns.lockable.LockableDataManager;
import nl.cwi.examples.ccconcerns.patterns.observer.Observable;
import nl.cwi.examples.ccconcerns.patterns.observer.ObservableDataManager;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class GeometryExample2 {

    public static void main(String[] args) {

        // ================================ Schema Schema ========================================

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        final Schema bootstrapSchema = SchemaLoader.bootLoad();

        final BasicDataManager basicFactory = new BasicDataManager();

        // Create a schema Factory which creates Schema instances.
        final SchemaFactory schemaFactory = basicFactory.factory(SchemaFactory.class, bootstrapSchema);

        System.out.println("===================================");
        System.out.println("LOAD FROM BOOT SCHEMA");
        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        final Schema realSchemaSchema = SchemaLoader.load(
            schemaFactory,
            Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        Klass realSchemaKlass = realSchemaSchema.klasses().stream().filter(klass -> "Schema".equals(klass.name())).findFirst().get();
        realSchemaSchema.schemaKlass(realSchemaKlass);

        DebugUtils.debugSchema(bootstrapSchema);
        System.out.println("=======================================");
        DebugUtils.debugSchema(realSchemaSchema);

        // =======================
        // Test equality
        System.out.println("===================================");
        System.out.println("LOAD FROM REAL SCHEMASCHEMA");
        final BasicDataManager basicFactory2 = new BasicDataManager();
        final SchemaFactory schemaFactory2 = basicFactory2.factory(SchemaFactory.class, realSchemaSchema);
        final Schema realSchemaSchema2 = SchemaLoader.load(
            schemaFactory2, Schema.class, Type.class, Primitive.class, Klass.class, Field.class, Primitives.class);

        System.out.println(" Check boot and real schema equality");
        boolean equalBootReal = MObjectUtils.equals(bootstrapSchema, realSchemaSchema);
        if (equalBootReal) {
            System.out.println("* EQUAL *");
        } else {
            System.err.println("Should be equal");
            System.exit(-1);
        }

        System.out.println(" Check real schema and real schema 2 equality");
        boolean equalRealReal = MObjectUtils.equals(realSchemaSchema, realSchemaSchema2);
        if (equalRealReal) {
            System.out.println("* EQUAL *");
        } else {
            System.err.println("Should be equal");
            System.exit(-1);
        }

        // ================================ Data objects ========================================
        System.out.println("=============");
        System.out.println("Data Objects: ");

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting props.
        // use the schemaFactory2, the schema factory which has been made from the realSchemaSchema
        final Schema pointSchema = SchemaLoader.load(
                schemaFactory2, Point.class, Point2D.class, Point3D.class, Line.class, Primitives.class);
        final BasicDataManager basicFactoryForPoints = new BasicDataManager();
        final PointFactory pointFactory = basicFactoryForPoints.factory(PointFactory.class, pointSchema);

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
           pointFactory.Point2D(1, 4)
        ));

        System.out.println("============");
        System.out.println("Set of 2D points: ");
        for (Point2D point2D : points) {
            System.out.println("x = " + point2D.x() + ", y = " + point2D.y());
        }

        // ================================ Observer ========================================
        System.out.println("=============");
        System.out.println("Observable Objects: ");

        final ObservableDataManager observableFactory = new ObservableDataManager();
        final PointFactory observablePointFactory = observableFactory.factory(PointFactory.class, pointSchema);

        // Create a new observer-record managed object.
        final Point3D observerPoint = observablePointFactory.Point3D();

        // Add Logging concerns
        ((Observable) observerPoint).addObserver(UpdateLogger::log);

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.z(9);
        observerPoint.x(observerPoint.x() + observerPoint.y());
        System.out.println(observerPoint.x() + observerPoint.y() + observerPoint.z());

        // ================================ Lockable ========================================
        System.out.println("=============");
        System.out.println("Lockable Objects: ");

        final LockableDataManager lockableFactory = new LockableDataManager();
        final PointFactory lockablePointFactory = lockableFactory.factory(PointFactory.class, pointSchema);
        final Point2D lockablePoint = lockablePointFactory.Point2D();
        lockablePoint.x(1);
        lockablePoint.y(1);

        // It was mutable until now, now it is locked (immutable).
        ((Lockable)lockablePoint).lock();

        try {
            lockablePoint.x(2); // Should throw here since its immutable.
        } catch (IllegalStateException e) {
            System.out.println("IllegalAccessError: " + e.getMessage());
        }
        System.out.println(lockablePoint.x());

        System.exit(0);
    }
}
