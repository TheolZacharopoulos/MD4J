package nl.cwi.examples.geometry.schemas;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;

/**
 * The Line Schema
 *
 * This schema is managed by a data manager capable of initialization
 * allowing the objects (points) to be created with starting props.
 */
public interface Line extends M {
    @Contain
    Point2D startPoint(Point2D... p);

    @Contain
    Point2D endPoint(Point2D... p);
}