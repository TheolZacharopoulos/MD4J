package com.cwi.managed_data.schemas.user_defined;

public interface PointFactory {
    Point point();
    Point point(Integer x, Integer y);
}