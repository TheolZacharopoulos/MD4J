package com.cwi;

import com.cwi.managed_data.managed_objects.factories.GenericFactory;
import com.cwi.managed_data.managed_objects.factories.InitializationFactory;
import com.cwi.managed_data.managed_objects.factories.ObservableFactory;
import com.cwi.managed_data.roles.Observable;
import com.cwi.managed_data.schemas.Person;
import com.cwi.managed_data.schemas.PersonFactory;
import com.cwi.managed_data.schemas.PointFactory;
import com.cwi.managed_data.schemas.Point;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Throwable {

        /**
         * Basic Record
         */
        System.out.println("Basic Record Point: ");

        // Create a Point factory which creates points (managed objects).
        PointFactory basicPointFactory = GenericFactory.newFactory(PointFactory.class);

        // Create a new basic-record managed object.
        Point basicPoint = basicPointFactory.point();
        basicPoint.x(3);
        basicPoint.y(7);
        System.out.println("\t " + (basicPoint.x() + basicPoint.y()));

        /**
         * Init Record
         */
        System.out.println("\nInit Record Point: ");

        // Create a Point factory which creates points (managed objects) with InitRecord data manager.
        PointFactory initPointFactory = InitializationFactory.newFactory(PointFactory.class);

        // Create a new init-record managed object.
        Point initPoint = initPointFactory.point(10, 22);
        System.out.println("\t " + (initPoint.x() + initPoint.y()));

        try {
            initPoint.x(44);
        } catch (IllegalAccessError e) {
            System.out.println("\t " + e.getMessage());
        }

        /**
         * Observer Record for Point
         */
        System.out.println("\nObserver Record Point: ");

        // Create a Point factory which creates points (manages objects) with ObserverRecord data manager.
        PointFactory observerPointFactory = ObservableFactory.newFactory(PointFactory.class);

        // Create a new observer-record managed object.
        Point observerPoint = observerPointFactory.point();

        ((Observable) observerPoint)
                .observe((obj, field, value) -> System.out.println("\t Updated " + field + " to " + value));

        observerPoint.x(1);
        observerPoint.y(6);
        observerPoint.x(observerPoint.x() + observerPoint.y());

        /**
         * Observer Record for Person.
         */
        System.out.println("\nObserver Record Person: ");

        PersonFactory observerPersonFactory = ObservableFactory.newFactory(PersonFactory.class);
        Person observerPerson = observerPersonFactory.person();

        ((Observable) observerPerson)
                .observe((obj, field, value) -> System.out.println("\t Updated " + field + " to " + value));

        observerPerson.name("Theologos");
        observerPerson.age(25);
        observerPerson.birthday(new Date());
    }
}