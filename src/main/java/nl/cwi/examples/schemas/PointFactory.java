package nl.cwi.examples.schemas;

public interface PointFactory {
    Point point(Integer x, Integer y);
    Line line(Point startPoint, Point endPoint);
}
