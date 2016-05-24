package nl.cwi.examples.geometry.schemas;

public interface Point2D extends Point {
    Integer x(Integer... x);
    Integer y(Integer... y);

    /**
     * Translates this point, at location {@code (x,y)},
     * by {@code dx} along the {@code x} axis and {@code dy}
     * along the {@code y} axis so that it now represents the point
     * {@code (x+dx,y+dy)}.
     *
     * @param       dx   the distance to move this point
     *                            along the X axis
     * @param       dy    the distance to move this point
     *                            along the Y axis
     */
    default void translate(int dx, int dy) {
        this.x(this.x() + dx);
        this.y(this.y() + dy);
    }
}
