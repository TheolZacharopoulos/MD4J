package com.cwi;

import com.cwi.managed_data.PointLogger;
import com.cwi.managed_data.data_managers.BasicRecord;
import com.cwi.managed_data.data_managers.InitRecord;
import com.cwi.managed_data.data_managers.ObserverRecord;
import com.cwi.managed_data.factories.GenericFactory;
import com.cwi.managed_data.factories.PointFactory;
import com.cwi.managed_data.schemas.Point;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) throws Throwable {

        /**
         * Basic Record
         */
        System.out.println("Basic Record Point: ");

        // Create a Point factory which creates points (manages objects) with BasicRecord data manager.
        PointFactory basicPointFactory = GenericFactory.newFactory(PointFactory.class, BasicRecord.class);

        // Create a new basic-record managed object.
        Point basicPoint = basicPointFactory.point();
        basicPoint.x(3);
        basicPoint.y(7);
        System.out.print(basicPoint.x() + basicPoint.y());


        /**
         * Init Record
         */
        System.out.println("\n\nInit Record Point: ");

        // Create a Point factory which creates points (manages objects) with InitRecord data manager.
        PointFactory initPointFactory = GenericFactory.newFactory(PointFactory.class, InitRecord.class);

        // Create a new init-record managed object.
        Point initPoint = initPointFactory.point(10, 22);
        System.out.print(initPoint.x() + initPoint.y());

        /**
         * Observer Record
         */
        System.out.println("\n\nObserver Record Point: ");

        // Create a Point factory which creates points (manages objects) with ObserverRecord data manager.
        PointFactory observerPointFactory = GenericFactory.newFactory(PointFactory.class, ObserverRecord.class);

        // Create a new observer-record managed object.
        Point observerPoint = observerPointFactory.point();

        // TODO: make this beautiful, mixin ???. to be used as: observerPoint.observe(log)
        ObserverRecord obsPointInvHandler =
                (ObserverRecord) Proxy.getInvocationHandler(observerPoint);

        Method log = PointLogger.class.getMethod("log", new Class<?>[] {String.class, Object.class});

        obsPointInvHandler.observe(log);

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.x(observerPoint.x() + observerPoint.y());
    }
}