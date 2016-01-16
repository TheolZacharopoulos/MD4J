package nl.cwi.examples.geometry;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

/**
 * The Line Schema
 *
 * This schema is managed by a data manager capable of initialization
 * allowing the objects (points) to be created with starting props.
 */
public interface Line {
    @Order(value = 1)
    Point2D startPoint(Point2D ...p);

    @Order(value = 2)
    Point2D endPoint(Point2D ...p);
}