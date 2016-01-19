package nl.cwi.examples.geometry;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Contain;

/**
 * The Line Schema
 *
 * This schema is managed by a data manager capable of initialization
 * allowing the objects (points) to be created with starting props.
 */
public interface Line {
    @Contain
    Point2D startPoint(Point2D ...p);

    @Contain
    Point2D endPoint(Point2D ...p);
}