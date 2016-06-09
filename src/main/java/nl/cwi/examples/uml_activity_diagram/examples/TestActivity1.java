package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes.RuntimeNodesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.EdgesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.Action;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityFinalNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.InitialNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.ValuesFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.VariablesFactory;

public class TestActivity1 {
    
    public static Activity getTestActivity(
            ValuesFactory valuesFactory,
            VariablesFactory variablesFactory,
            RuntimeNodesFactory nodesFactory,
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
        InitialNode initialNode1 = nodesFactory.InitialNode();
        initialNode1.name("initialNode1");

        Action action1 = nodesFactory.Action();
        action1.name("action1");

        ActivityFinalNode finalNode1 = nodesFactory.ActivityFinalNode();
        finalNode1.name("finalNode1");

        // edges
        ControlFlow edge1 = edgesFactory.ControlFlow();
        edge1.name("edge1");
        edge1.guard(defaultFalse);
        edge1.source(initialNode1);
        edge1.target(action1);

        ControlFlow edge2 = edgesFactory.ControlFlow();
        edge2.name("edge2");
        edge2.guard(defaultFalse);
        edge2.source(action1);
        edge1.target(finalNode1);

        // Activity
        Activity test1 = activityFactory.Activity();
        test1.name("test1");

        test1.edges(edge1, edge2);
        test1.nodes(initialNode1, action1, finalNode1);

        return test1;
    }
}
