package schemas.static_diagram.nodes;

import nl.cwi.managed_data_4j.IFactory;

public interface NodesFactory extends IFactory {
    InitialNode InitialNode();
    FinalNode FinalNode();
    DecisionNode DecisionNode();
    ForkNode ForkNode();
    JoinNode JoinNode();
    MergeNode MergeNode();
    ActivityFinalNode ActivityFinalNode();
    Action Action();
    OpaqueAction OpaqueAction();
}
