package com.cwi.managed_data.factories;

import com.cwi.managed_data.schemas.Point;

public interface PointFactory {
    Point point(Integer x, Integer y);
}