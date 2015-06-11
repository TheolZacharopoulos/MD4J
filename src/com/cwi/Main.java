package com.cwi;

import com.cwi.managed_data.PointLogger;
import com.cwi.managed_data.data_managers.BasicRecord;
import com.cwi.managed_data.data_managers.InitRecord;
import com.cwi.managed_data.data_managers.ObserverRecord;
import com.cwi.managed_data.factories.Factory;
import com.cwi.managed_data.factories.GenericFactory;
import com.cwi.managed_data.factories.PointFactory;
import com.cwi.managed_data.schemas.Point;
import com.cwi.managed_data.schemas.Schema;
import com.cwi.managed_data.schemas.ToSchema;
import sun.jvm.hotspot.oops.Klass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Throwable {

//        PointFactory pointFactory = (PointFactory) GenericFactory.newFactory(PointFactory.class); // something like that.
//
//        Schema pointSchema = ToSchema.convert(Point.class); // We need a conversion from sets of java interfaces to instances of Schema.
//
//        Point basicPoint = pointFactory.point(pointSchema);


        Point point = (Point) Factory.make(Point.class, new BasicRecord(Point.class));

        point.x(10);
        point.y(15);

        System.out.print(point.x() + point.y());

    }
        
//        // Base
//        System.out.println("Base point: ");
//        Point basicPoint = (Point) BasicRecord.newInstance(Point.class);
//        basicPoint.x(3);
//        basicPoint.y(-10);
//        System.out.println("\t" + basicPoint.x() + " " + basicPoint.y());
//
//        // Wrong Type
//        System.out.println("\nTrying to _set wrong type: ");
//        try {
//            Point initPointWrongType = (Point) InitRecord.newInstance(
//                    Point.class, new HashMap<String, Object>(){ // TODO: Map as argument looks ugly.
//                        {   put("x", "test");
//                            put("y", 66);
//                        }
//                    });
//        System.out.println("\t" + initPointWrongType.x() + " " + initPointWrongType.y() + "\n");
//        } catch (Exception e) {
//            System.out.println("\t" + e.getMessage());
//        }
//
//        System.out.println("\nInit & immutable: ");
//
//        // Init & lockable.
//        Point initPoint = (Point) InitRecord.newInstance(
//                Point.class, new HashMap<String, Object>() { // TODO: Map as argument looks ugly.
//                    {
//                        put("x", 6);
//                        put("y", 66);
//                    }
//                });
//        System.out.println("\t" + initPoint.x() + " " + initPoint.y() + "\n");
//
//        System.out.println("Trying to change an immutable: ");
//        try {
//            initPoint.x(3);
//        } catch (IllegalAccessError e) {
//            System.out.println("\t" + e.getMessage());
//        }
//
//        // Observer
//        System.out.println("\n Observer Pattern: ");
//        Point obsPoint = (Point)
//                ObserverRecord.newInstance(Point.class);
//
//        ObserverRecord obsPointInvHandler =
//                (ObserverRecord) Proxy.getInvocationHandler(obsPoint);
//
//        Method log = PointLogger.class.getMethod("log", new Class<?>[] {String.class, Object.class});
//
//        obsPointInvHandler.observe(log);
//
//        obsPoint.x(1);
//        obsPoint.y(6);
//        obsPoint.x(obsPoint.x() + obsPoint.y());
//    }
}