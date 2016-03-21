package nl.cwi.examples.geometry.data_managers;

import nl.cwi.examples.geometry.schemas.Line;
import nl.cwi.examples.geometry.schemas.Point2D;
import nl.cwi.examples.geometry.schemas.Point3D;

/**
 * The Point Schema Factory.
 *
 * This is used to create instances of Point, and Line objects.
 */
public interface PointFactory {
    Point2D point2D();
    Point2D point2D(Integer x, Integer y);

    Point3D point3D();
    Point3D point3D(Integer x, Integer y, Integer z);

    Line line();
}
