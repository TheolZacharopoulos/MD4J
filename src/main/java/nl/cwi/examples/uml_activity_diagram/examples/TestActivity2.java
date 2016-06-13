package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;

public class TestActivity2 {

    public static Activity getTestActivity(ActivityFactory f) {

        OpaqueAction action3_3 = f.OpaqueAction();
        action3_3.name("action3");

        OpaqueAction action2_2 = f.OpaqueAction();
        action2_2.name("action2");

        ForkNode forkNode1_1 = f.ForkNode();
        forkNode1_1.name("forkNode1");

        JoinNode joinNode1_4 = f.JoinNode();
        joinNode1_4.name("joinNode1");

        ActivityFinalNode finalNode2_5 = f.ActivityFinalNode();
        finalNode2_5.name("finalNode2");

        InitialNode initialNode2_0 = f.InitialNode();
        initialNode2_0.name("initialNode2");

        ActivityEdge edge5_8 = f.ActivityEdge();
        edge5_8.name("edge5");

        ActivityEdge edge4_7 = f.ActivityEdge();
        edge4_7.name("edge4");

        ActivityEdge edge8_11 = f.ActivityEdge();
        edge8_11.name("edge8");

        ActivityEdge edge3_6 = f.ActivityEdge();
        edge3_6.name("edge3");

        ActivityEdge edge7_10 = f.ActivityEdge();
        edge7_10.name("edge7");

        ActivityEdge edge6_9 = f.ActivityEdge();
        edge6_9.name("edge6");

        edge5_8.source(forkNode1_1);
        edge5_8.target(action3_3);

        edge4_7.source(forkNode1_1);
        edge4_7.target(action2_2);

        edge8_11.source(joinNode1_4);
        edge8_11.target(finalNode2_5);

        edge3_6.source(initialNode2_0);
        edge3_6.target(forkNode1_1);

        edge7_10.source(action3_3);
        edge7_10.target(joinNode1_4);

        edge6_9.source(action2_2);
        edge6_9.target(joinNode1_4);

        action3_3.outgoing(edge7_10);
        action3_3.incoming(edge5_8);

        action3_3.expressions();

        action2_2.outgoing(edge6_9);
        action2_2.incoming(edge4_7);

        action2_2.expressions();

        forkNode1_1.outgoing(edge4_7, edge5_8);
        forkNode1_1.incoming(edge3_6);

        joinNode1_4.outgoing(edge8_11);
        joinNode1_4.incoming(edge6_9, edge7_10);

        finalNode2_5.outgoing();
        finalNode2_5.incoming(edge8_11);

        initialNode2_0.outgoing(edge3_6);
        initialNode2_0.incoming();

        Activity test2_12 = f.Activity();
        test2_12.name("test2");
        test2_12.locals();
        test2_12.inputs();
        test2_12.nodes(action3_3, action2_2, forkNode1_1, joinNode1_4, initialNode2_0, finalNode2_5);
        test2_12.edges(edge5_8, edge4_7, edge8_11, edge3_6, edge7_10, edge6_9);

        return test2_12;
    }
}
