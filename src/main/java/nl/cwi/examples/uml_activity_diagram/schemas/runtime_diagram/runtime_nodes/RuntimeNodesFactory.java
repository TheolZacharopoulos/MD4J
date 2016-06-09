package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.NodesFactory;

public interface RuntimeNodesFactory extends NodesFactory {
    RuntimeInitialNode InitialNode();
    RuntimeFinalNode FinalNode();
    RuntimeDecisionNode DecisionNode();
    RuntimeForkNode ForkNode();
    RuntimeJoinNode JoinNode();
    RuntimeMergeNode MergeNode();
    RuntimeActivityFinalNode ActivityFinalNode();
    RuntimeAction Action();
    RuntimeOpaqueAction OpaqueAction();
}
