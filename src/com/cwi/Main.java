package com.cwi;

import com.cwi.managed_data.data_managers.BasicRecordProxy;
import com.cwi.managed_data.data_managers.InitRecordProxy;
import com.cwi.managed_data.schemas.Point;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        // Base
        System.out.println("Base point: ");
        Point basicPoint = (Point) BasicRecordProxy.newInstance(Point.class);
        basicPoint.x(3);
        basicPoint.y(-10);
        System.out.println("\t" + basicPoint.x() + " " + basicPoint.y());

        // Wrong Type
        System.out.println("\nTrying to set wrong type: ");
        try {
            Point initPointWrongType = (Point) InitRecordProxy.newInstance(
                    Point.class, new HashMap<String, Object>(){ // TODO: Map as argument looks ugly.
                        {   put("x", "test");
                            put("y", 66);
                        }
                    });
        System.out.println("\t" + initPointWrongType.x() + " " + initPointWrongType.y() + "\n");
        } catch (Exception e) {
            System.out.println("\t" + e.getMessage());
        }

        System.out.println("\nInit & immutable: ");
        // Init & lockable.
        Point initPoint = (Point) InitRecordProxy.newInstance(
                Point.class, new HashMap<String, Object>(){ // TODO: Map as argument looks ugly.
                    { put("x", 6);
                      put("y", 66);
                    }
                });
        System.out.println("\t" + initPoint.x() + " " + initPoint.y() + "\n");

        System.out.println("Trying to change an immutable: ");
        try {
            initPoint.x(3);
        } catch (IllegalAccessError e) {
            System.out.println("\t" + e.getMessage());
        }
    }
}