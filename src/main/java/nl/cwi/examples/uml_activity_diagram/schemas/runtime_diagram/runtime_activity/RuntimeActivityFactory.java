package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_activity;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_edges.RuntimeControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.ControlToken;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.ForkedToken;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens.Token;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Offer;
import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.trace.Trace;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;

public interface RuntimeActivityFactory extends ActivityFactory {
    // Activity
    RuntimeActivity Activity();

    // Edges
    RuntimeActivityEdge ActivityEdge();
    RuntimeControlFlow ControlFlow();

    // Nodes
    RuntimeInitialNode InitialNode();
    RuntimeFinalNode FinalNode();
    RuntimeDecisionNode DecisionNode();
    RuntimeForkNode ForkNode();
    RuntimeJoinNode JoinNode();
    RuntimeMergeNode MergeNode();
    RuntimeActivityFinalNode ActivityFinalNode();
    RuntimeAction Action();
    RuntimeOpaqueAction OpaqueAction();

    // Tokens
    Token Token();
    ForkedToken ForkedToken();
    ControlToken ControlToken();

    Offer Offer();

    // Trace
    Trace Trace();
}
