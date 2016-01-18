package nl.cwi.examples.geometry;

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
