package schemas.runtime_diagram.runtime_nodes;

import schemas.static_diagram.nodes.NodesFactory;

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
