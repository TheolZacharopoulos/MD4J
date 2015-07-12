package com.cwi.managed_data.schemas;

public interface PointFactory {
    Point point();
    Point point(Integer x, Integer y);
}