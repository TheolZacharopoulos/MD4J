package nl.cwi.examples.geometry;

import nl.cwi.managed_data_4j.schema.models.definition.annotations.Order;

public interface Point2D extends Point {
    @Order(value = 1)
    Integer x(Integer... x);

    @Order(value = 2)
    Integer y(Integer... y);
}
