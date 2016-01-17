package nl.cwi.examples.geometry;

/**
 * The Point Schema Factory.
 *
 * This is used to create instances of Point, and Line objects.
 */
public interface PointFactory {
    Point2D point2D();
    Point3D point3D();
    Line line();
}
