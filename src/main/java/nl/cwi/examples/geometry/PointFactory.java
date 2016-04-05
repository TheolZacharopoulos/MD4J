package nl.cwi.examples.geometry;

import nl.cwi.examples.geometry.schemas.Line;
import nl.cwi.examples.geometry.schemas.Point2D;
import nl.cwi.examples.geometry.schemas.Point3D;

/**
 * The Point Schema Factory.
 *
 * This is used to create instances of Point, and Line objects.
 */
public interface PointFactory {
    Point2D Point2D();
    Point2D Point2D(Integer x, Integer y);

    Point3D Point3D();
    Point3D Point3D(Integer x, Integer y, Integer z);

    Line Line();
}
