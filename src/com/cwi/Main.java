package com.cwi;

import com.cwi.managed_data.data_manager.BasicRecordProxy;
import com.cwi.managed_data.schemas.Point;

public class Main {

    public static void main(String[] args) {
        Point p = (Point) BasicRecordProxy.newInstance(Point.class);
        p.x(3);
        p.y(-10);
        System.out.println(p.x() + " " + p.y());
    }
}