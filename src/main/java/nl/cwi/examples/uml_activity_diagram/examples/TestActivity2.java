package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.ActivityDiagramFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public class TestActivity2 {
    
    public static Activity getTestActivity(ActivityDiagramFactory activityDiagramFactory)
    {
        // Variables
        BooleanValue falseValue = activityDiagramFactory.BooleanValue();
        falseValue.value(false);

        BooleanVariable defaultFalse = activityDiagramFactory.BooleanVariable();
        defaultFalse.name("false");
        defaultFalse.currentValue(falseValue);

        // nodes
        InitialNode initialNode2 = activityDiagramFactory.InitialNode();
        initialNode2.name("initialNode2");

        ForkNode forkNode1 = activityDiagramFactory.ForkNode();
        forkNode1.name("forkNode1");

        Action action2 = activityDiagramFactory.Action();
        action2.name("action2");

        Action action3 = activityDiagramFactory.Action();
        action3.name("action3");

        JoinNode joinNode1 = activityDiagramFactory.JoinNode();
        joinNode1.name("joinNode1");

        ActivityFinalNode finalNode2 = activityDiagramFactory.ActivityFinalNode();
        finalNode2.name("finalNode2");

        // edges
        ControlFlow edge3 = activityDiagramFactory.ControlFlow();
        edge3.name("edge3");
        edge3.guard(defaultFalse);
        edge3.source(initialNode2);
        edge3.target(forkNode1);

        ControlFlow edge4 = activityDiagramFactory.ControlFlow();
        edge4.name("edge4");
        edge4.guard(defaultFalse);
        edge4.source(forkNode1);
        edge4.target(action2);

        ControlFlow edge5 = activityDiagramFactory.ControlFlow();
        edge5.name("edge5");
        edge5.guard(defaultFalse);
        edge5.source(forkNode1);
        edge5.target(action3);

        ControlFlow edge6 = activityDiagramFactory.ControlFlow();
        edge6.name("edge6");
        edge6.guard(defaultFalse);
        edge6.source(action2);
        edge6.target(joinNode1);

        ControlFlow edge7 = activityDiagramFactory.ControlFlow();
        edge7.name("edge7");
        edge7.guard(defaultFalse);
        edge7.source(action3);
        edge7.target(joinNode1);

        ControlFlow edge8 = activityDiagramFactory.ControlFlow();
        edge8.name("edge8");
        edge8.guard(defaultFalse);
        edge8.source(joinNode1);
        edge8.target(finalNode2);

        // Activity
        Activity test2 = activityDiagramFactory.Activity();
        test2.name("test2");

        test2.edges(edge3, edge4, edge5, edge6, edge7, edge8);
        test2.nodes(initialNode2, action2, action3, joinNode1, forkNode1, finalNode2);

        return test2;
    }
}
