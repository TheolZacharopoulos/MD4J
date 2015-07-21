package com.cwi.managed_data.examples.schemas;

public interface PointFactory {
    Point point();
    Point point(Integer x, Integer y);
}