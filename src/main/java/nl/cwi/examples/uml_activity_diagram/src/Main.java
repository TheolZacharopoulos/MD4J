import examples.TestActivity1;
//import examples.TestActivity2;
import helpers.FactoriesProvider;
import schemas.runtime_diagram.runtime_activity.RuntimeActivity;
import schemas.static_diagram.activity.Activity;
import schemas.static_diagram.nodes.ActivityNode;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        final RuntimeActivity activity = (RuntimeActivity) TestActivity1.getTestActivity(
            FactoriesProvider.getValuesFactory(),
            FactoriesProvider.getVariablesFactory(),
            FactoriesProvider.getRuntimeNodesFactory(),
            FactoriesProvider.getRuntimeEdgesFactory(),
            FactoriesProvider.getRuntimeActivityFactory());

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
