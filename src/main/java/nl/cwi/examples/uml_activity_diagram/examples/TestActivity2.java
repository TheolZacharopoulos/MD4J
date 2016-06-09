package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.EdgesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.ValuesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.VariablesFactory;

public class TestActivity2 {
    
    public static Activity getTestActivity(
            ValuesFactory valuesFactory,
            VariablesFactory variablesFactory,
            NodesFactory nodesFactory,
            EdgesFactory edgesFactory,
            ActivityFactory activityFactory)
    {
        // Variables
        BooleanValue falseValue = valuesFactory.BooleanValue();
        falseValue.value(false);

        BooleanVariable defaultFalse = variablesFactory.BooleanVariable();
        defaultFalse.name("false");
        defaultFalse.currentValue(falseValue);

        // nodes
        InitialNode initialNode2 = nodesFactory.InitialNode();
        initialNode2.name("initialNode2");

        ForkNode forkNode1 = nodesFactory.ForkNode();
        forkNode1.name("forkNode1");

        Action action2 = nodesFactory.Action();
        action2.name("action2");

        Action action3 = nodesFactory.Action();
        action3.name("action3");

        JoinNode joinNode1 = nodesFactory.JoinNode();
        joinNode1.name("joinNode1");

        ActivityFinalNode finalNode2 = nodesFactory.ActivityFinalNode();
        finalNode2.name("finalNode2");

        // edges
        ControlFlow edge3 = edgesFactory.ControlFlow();
        edge3.name("edge3");
        edge3.guard(defaultFalse);
        edge3.source(initialNode2);
        edge3.target(forkNode1);

        ControlFlow edge4 = edgesFactory.ControlFlow();
        edge4.name("edge4");
        edge4.guard(defaultFalse);
        edge4.source(forkNode1);
        edge4.target(action2);

        ControlFlow edge5 = edgesFactory.ControlFlow();
        edge5.name("edge5");
        edge5.guard(defaultFalse);
        edge5.source(forkNode1);
        edge5.target(action3);

        ControlFlow edge6 = edgesFactory.ControlFlow();
        edge6.name("edge6");
        edge6.guard(defaultFalse);
        edge6.source(action2);
        edge6.target(joinNode1);

        ControlFlow edge7 = edgesFactory.ControlFlow();
        edge7.name("edge7");
        edge7.guard(defaultFalse);
        edge7.source(action3);
        edge7.target(joinNode1);

        ControlFlow edge8 = edgesFactory.ControlFlow();
        edge8.name("edge8");
        edge8.guard(defaultFalse);
        edge8.source(joinNode1);
        edge8.target(finalNode2);

        // Activity
        Activity test2 = activityFactory.Activity();
        test2.name("test2");

        test2.edges(edge3, edge4, edge5, edge6, edge7, edge8);
        test2.nodes(initialNode2, action2, action3, joinNode1, forkNode1, finalNode2);

        return test2;
    }
}
