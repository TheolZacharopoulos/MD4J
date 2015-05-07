package com.cwi;

import com.cwi.managed_data.data_managers.BasicRecordProxy;
import com.cwi.managed_data.data_managers.InitRecordProxy;
import com.cwi.managed_data.schemas.Point;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // Base
        Point basicPoint = (Point) BasicRecordProxy.newInstance(Point.class);
        basicPoint.x(3);
        basicPoint.y(-10);
        System.out.println(basicPoint.x() + " " + basicPoint.y());

        // Init & lockable.
        Point initPoint = (Point) InitRecordProxy.newInstance(
                Point.class, new HashMap<String, Object>(){ // TODO: Map as argument looks ugly.
                    { put("x", 8);
                      put("y", 66);
                    }
                });
        try {
            initPoint.x(3);
        } catch (IllegalAccessError e) {
            System.out.println(e.getMessage());
        }
        System.out.println(initPoint.x() + " " + initPoint.y());
    }
}