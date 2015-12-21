package nl.cwi.managed_data_4j;

import nl.cwi.examples.schemas.Line;
import nl.cwi.examples.schemas.Point;
import nl.cwi.examples.schemas.PointFactory;
import nl.cwi.managed_data_4j.data_managers.BasicFactory;
import nl.cwi.managed_data_4j.klass_system.models.bootstrap.SchemaFactory;
import nl.cwi.managed_data_4j.klass_system.helpers.SchemaManager;

// Import the SchemaSchema definition
import nl.cwi.managed_data_4j.klass_system.models.schema_schema.*;

public class Main {

    public static void main(String[] args) {

        // We bootstrap the SchemaSchema from the minimal BootstrapSchema that has only classes and fields.
        // This minimal bootstrap schema is necessarily self-describing as it must change itself and
        // it processes simplistic data managers that only allow updating.
        // It is also hardcoded.
        Schema bootstrapSchema = SchemaManager.bootLoad();

        SchemaFactory schemaFactory = BasicFactory.make(SchemaFactory.class, bootstrapSchema);

        // The schemas are described by the SchemaSchema.
        // This schemaSchema is also self-describing.
        Schema realSchemaSchema = SchemaManager.load(schemaFactory);
        assert (bootstrapSchema == realSchemaSchema);

        // Data objects (like Point) are described by schemas (like the Point interface)
        // This schema is managed by a data manager capable of initialization allowing the objects
        // (points) to be created with starting values.
//        Schema pointSchema = SchemaManager.load(schemaFactory, Point.class, Line.class);
//        PointFactory pointFactory = BasicFactory.make(PointFactory.class, pointSchema);
//        Point point = pointFactory.point(3, 2);
    }
}
