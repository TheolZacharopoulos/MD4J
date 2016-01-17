package nl.cwi.examples.geometry;

/**
 * The Line Schema
 *
 * This schema is managed by a data manager capable of initialization
 * allowing the objects (points) to be created with starting props.
 */
public interface Line {
    Point2D startPoint(Point2D ...p);
    Point2D endPoint(Point2D ...p);
}