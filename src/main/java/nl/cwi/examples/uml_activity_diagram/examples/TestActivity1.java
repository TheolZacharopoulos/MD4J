package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityFinalNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.InitialNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.OpaqueAction;

public class TestActivity1 {
    
    public static Activity getTestActivity(ActivityFactory f) {

        OpaqueAction action1_1 = f.OpaqueAction();
        action1_1.name("action1");

        ActivityFinalNode finalNode1_2 = f.ActivityFinalNode();
        finalNode1_2.name("finalNode1");

        InitialNode initialNode1_0 = f.InitialNode();
        initialNode1_0.name("initialNode1");

        ActivityEdge edge1_3 = f.ActivityEdge();
        edge1_3.name("edge1");

        ActivityEdge edge2_4 = f.ActivityEdge();
        edge2_4.name("edge2");

        edge1_3.source(initialNode1_0);
        edge1_3.target(action1_1);

        edge2_4.source(action1_1);
        edge2_4.target(finalNode1_2);

        action1_1.outgoing(edge2_4);
        action1_1.incoming(edge1_3);

        action1_1.expressions();

        finalNode1_2.outgoing();
        finalNode1_2.incoming(edge2_4);

        initialNode1_0.outgoing(edge1_3);
        initialNode1_0.incoming();

        Activity test1_5 = f.Activity();
        test1_5.name("test1");
        test1_5.locals();
        test1_5.inputs();
        test1_5.nodes(action1_1, initialNode1_0);
        test1_5.edges(edge1_3, edge2_4);

        finalNode1_2.activity(test1_5);

        return test1_5;
    }
}
