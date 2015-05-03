package com.cwi.managed_data;

interface Point {
    Integer x(Integer ...x);
    Integer y(Integer ...y);
}

public class Main {

    public static void main(String[] args) {
        Point p = (Point) BasicRecordProxy.newInstance(Point.class);
        p.x(4);
        p.y();
    }
}