package nl.cwi.examples.uml_activity_diagram;

import nl.cwi.examples.uml_activity_diagram.examples.TestPerformanceVariant3_2;
import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        final RuntimeActivity activity = (RuntimeActivity) TestPerformanceVariant3_2
                .testperformance_variant3_2(FactoriesProvider.getRuntimeActivityDiagramFactory());

        double start = System.currentTimeMillis();

        activity.main(Collections.emptyList());

        double end = System.currentTimeMillis();

        printTrace(activity);

        System.out.println("Total seconds: " + ((end - start)/1000)%60);
    }

    static void printTrace(Activity a) {
        System.out.println("TRACE for activity " + a.name() + ": ");

        for (ActivityNode activityNode : a.trace().executedNodes()) {
            System.out.println(" " + activityNode.name());
        }
    }
}
