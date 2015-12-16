package nl.cwi.examples.schemas;

public interface Line {
    Point startPoint(Point ...p);
    Point endPoint(Point ...p);
}