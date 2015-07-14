package com.cwi;

import com.cwi.managed_data.factories.Factory;
import com.cwi.managed_data.factories.GenericFactory;
import com.cwi.managed_data.schemas.PointFactory;
import com.cwi.managed_data.schemas.Point;

public class Main {

    public static void main(String[] args) throws Throwable {

        PointFactory factory = GenericFactory.newFactory(PointFactory.class);
        Point p = factory.point();
        p.x(3);
        p.y(7);
        System.out.println("\t " + (p.x() + p.y()));

//        /**
//         * Basic Record
//         */
//        System.out.println("Basic Record Point: ");
//
//        // Create a Point factory which creates points (managed objects).
//        PointFactory basicPointFactory = (PointFactory) Factory.make(PointFactory.class);
//
//        // Create a new basic-record managed object.
//        Point basicPoint = basicPointFactory.point();
//        basicPoint.x(3);
//        basicPoint.y(7);
//        System.out.println("\t " + (basicPoint.x() + basicPoint.y()));
//
//        /**
//         * Init Record
//         */
//        System.out.println("\nInit Record Point: ");
//
//        // Create a Point factory which creates points (managed objects) with InitRecord data manager.
//        PointFactory initPointFactory = (PointFactory) InitializationFactory.make(PointFactory.class);
//
//        // Create a new init-record managed object.
//        Point initPoint = initPointFactory.point(10, 22);
//        System.out.println("\t " + (initPoint.x() + initPoint.y()));
//
//        try {
//            initPoint.x(44);
//        } catch (IllegalAccessError e) {
//            System.out.println("\t " + e.getMessage());
//        }
//
//        /**
//         * Observer Record
//         */
//        System.out.println("\nObserver Record Point: ");
//
//        // Create a Point factory which creates points (manages objects) with ObserverRecord data manager.
//        PointFactory observerPointFactory = (PointFactory) ObservableFactory.make(PointFactory.class);
//
//        // Create a new observer-record managed object.
//        Point observerPoint = observerPointFactory.point();
//
//        ((Observable) observerPoint)
//                .observe((obj, name, value) -> System.out.println("\t Updated " + name + " to " + value));
//
//        observerPoint.x(1);
//        observerPoint.y(6);
//        observerPoint.x(observerPoint.x() + observerPoint.y());
    }
}