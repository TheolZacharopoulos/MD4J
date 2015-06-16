package com.cwi;

import com.cwi.managed_data.factories.Factory;
import com.cwi.managed_data.factories.InitializationFactory;
import com.cwi.managed_data.factories.ObservableFactory;
import com.cwi.managed_data.schemas.user_defined.PointFactory;
import com.cwi.managed_data.schemas.user_defined.Point;

public class Main {

    public static void main(String[] args) throws Throwable {

        /**
         * Basic Record
         */
        System.out.println("Basic Record Point: ");

        // Create a Point factory which creates points (managed objects).
        PointFactory basicPointFactory = (PointFactory) Factory.make(PointFactory.class);

        // Create a new basic-record managed object.
        Point basicPoint = basicPointFactory.point();
        basicPoint.x(3);
        basicPoint.y(7);
        System.out.print(basicPoint.x() + basicPoint.y());

        /**
         * Init Record
         */
        System.out.println("\n\nInit Record Point: ");

        // Create a Point factory which creates points (managed objects) with InitRecord data manager.
        PointFactory initPointFactory = (PointFactory) InitializationFactory.make(PointFactory.class);

        // Create a new init-record managed object.
        Point initPoint = initPointFactory.point(10, 22);
        System.out.println(initPoint.x() + initPoint.y());

        /**
         * Observer Record
         */
        System.out.println("\n\nObserver Record Point: ");

        // Create a Point factory which creates points (manages objects) with ObserverRecord data manager.
        PointFactory observerPointFactory = (PointFactory) ObservableFactory.make(PointFactory.class);

        // Create a new observer-record managed object.
        Point observerPoint = observerPointFactory.point();

        // TODO: cannot do this now because this is a point not an Observable object.
        // observerPoint.observe((name, value) -> System.out.println("\t Updated " + name + " to " + value));

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.x(observerPoint.x() + observerPoint.y());
    }
}