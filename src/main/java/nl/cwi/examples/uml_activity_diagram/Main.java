package nl.cwi.examples.uml_activity_diagram;

import nl.cwi.examples.uml_activity_diagram.examples.TestPerformanceVariant_2;
import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity.RuntimeActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        System.out.println("> Building schemas and factories");
        final RuntimeActivityFactory factory = FactoriesProvider.getRuntimeActivityDiagramFactory();

        System.out.println("> Building uml activity diagram");
        final RuntimeActivity activity = (RuntimeActivity) TestPerformanceVariant_2.getActivity(factory);

        System.out.println("> Executing uml activity diagram...");

        double start = System.currentTimeMillis();

        activity.main(Collections.emptyList());

        double end = System.currentTimeMillis();

        printActivity(activity);

        printTrace(activity);

        System.out.println("Total seconds: " + ((end - start)/1000)%60);
    }

    static void printTrace(RuntimeActivity a) {
        System.out.println("TRACE for activity " + a.name() + ": ");

        for (ActivityNode activityNode : a.trace().executedNodes()) {
            System.out.println(" " + activityNode.name());
        }
    }

    static void printActivity(Activity a) {
        System.out.println("Activity: " + a.name());
        a.nodes().forEach(activityNode -> {
            System.out.println("> Node: " + activityNode.name());

            System.out.println("\t from: ");
            activityNode.incoming().forEach(edge -> System.out.println("\t > incomingEdge: " + edge.name()));

            System.out.println("\t to: ");
            activityNode.outgoing().forEach(edge -> System.out.println("\t > outgoingEdge: " + edge.name()));
        });
    }
}
