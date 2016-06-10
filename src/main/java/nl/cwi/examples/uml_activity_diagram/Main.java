package nl.cwi.examples.uml_activity_diagram;

import nl.cwi.examples.uml_activity_diagram.examples.TestActivity2;
import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        final RuntimeActivity activity = (RuntimeActivity)
                TestActivity2.getTestActivity(FactoriesProvider.getRuntimeActivityDiagramFactory());

        activity.main(Collections.emptyList());

        System.out.println("===============");

        printTrace(activity);
    }

    static void printTrace(Activity a) {
        System.out.println("TRACE for " + a.name() + ": ");

        for (ActivityNode activityNode : a.trace().executedNodes()) {
            System.out.println("  > " + activityNode.name());
        }
    }
}
