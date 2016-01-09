package nl.cwi.managed_data_4j;

import nl.cwi.examples.ccc.UpdateLogger;
import nl.cwi.examples.geometry.Line;
import nl.cwi.examples.geometry.Point;
import nl.cwi.examples.geometry.PointFactory;
import nl.cwi.examples.patterns.observer.Observable;
import nl.cwi.examples.patterns.observer.ObservableFactory;
import nl.cwi.managed_data_4j.data_managers.BasicFactory;
import nl.cwi.managed_data_4j.data_managers.DataManagerFactory;
import nl.cwi.managed_data_4j.schema.boot.SchemaFactory;
import nl.cwi.managed_data_4j.schema.load.SchemaLoader;

import nl.cwi.managed_data_4j.schema.models.schema_schema.*;

public class Main {

    public static void main(String[] args) {

        // ================================ Schema Schema ========================================

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        Schema bootstrapSchema = SchemaLoader.bootLoad();

        final BasicFactory basicFactory = new BasicFactory();

        // Create a schema Factory which creates Schema instances.
        SchemaFactory schemaFactory = DataManagerFactory.make(basicFactory, SchemaFactory.class, bootstrapSchema);;

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        Schema realSchemaSchema =
                SchemaLoader.load(schemaFactory, Schema.class, Type.class, Primitive.class, Klass.class, Field.class);
        assert (bootstrapSchema == realSchemaSchema);

        // ================================ Data objects ========================================

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting props.
        Schema pointSchema = SchemaLoader.load(schemaFactory, Point.class, Line.class);
        PointFactory pointFactory = DataManagerFactory.make(basicFactory, PointFactory.class, pointSchema);
        Point point = pointFactory.point(3, 2);
        Line line = pointFactory.line(point, point);

        System.out.print(point.x() + " + " + point.y() + " = ");
        System.out.print(point.x() + point.y());
        System.out.print(" == ");
        System.out.println(line.startPoint().x() + line.startPoint().y());

        // ================================ Observer ========================================
        final ObservableFactory observableFactory = new ObservableFactory();
        PointFactory observablePointFactory = DataManagerFactory.make(observableFactory, PointFactory.class, pointSchema);

        // Create a new observer-record managed object.
        Point observerPoint = observablePointFactory.point();

        // Add Logging concerns
        ((Observable) observerPoint).observe(UpdateLogger::log);

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.x(observerPoint.x() + observerPoint.y());
        System.out.println(observerPoint.x());
    }
}
