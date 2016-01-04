package nl.cwi.examples.geometry;

/**
 * The Point Schema Factory.
 *
 * This is used to create instances of Point, and Line objects.
 */
public interface PointFactory {
    Point point(Integer x, Integer y);
    Line line(Point startPoint, Point endPoint);
}
