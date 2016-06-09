package nl.cwi.examples.uml_activity_diagram.schemas;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;
import nl.cwi.managed_data_4j.IFactory;

public interface ActivityDiagramFactory extends IFactory {
    // Activity
    Activity Activity();

    // Edges
    ActivityEdge ActivityEdge();
    ControlFlow ControlFlow();

    // Nodes
    InitialNode InitialNode();
    FinalNode FinalNode();
    DecisionNode DecisionNode();
    ForkNode ForkNode();
    JoinNode JoinNode();
    MergeNode MergeNode();
    ActivityFinalNode ActivityFinalNode();
    Action Action();
    OpaqueAction OpaqueAction();

    // Values
    IntegerValue IntegerValue();
    BooleanValue BooleanValue();

    // Variables
    IntegerVariable IntegerVariable();
    BooleanVariable BooleanVariable();

}
