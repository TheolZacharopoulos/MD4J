package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public class TestActivity3 {
    
    public static Activity getActivity(ActivityFactory f) {

        BooleanValue trueValue = f.BooleanValue();
        trueValue.value(true);

        BooleanValue falseValue = f.BooleanValue();
        falseValue.value(false);

        BooleanVariable var1_0 = f.BooleanVariable();
        var1_0.name("var1");
        var1_0.initialValue(trueValue);

        BooleanVariable var2_1 = f.BooleanVariable();
        var2_1.name("var2");
        var2_1.initialValue(falseValue);

        OpaqueAction action5_5 = f.OpaqueAction();
        action5_5.name("action5");

        OpaqueAction action4_4 = f.OpaqueAction();
        action4_4.name("action4");

        DecisionNode decisionNode1_3 = f.DecisionNode();
        decisionNode1_3.name("decisionNode1");

        ActivityFinalNode finalNode3_7 = f.ActivityFinalNode();
        finalNode3_7.name("finalNode3");

        MergeNode mergeNode1_6 = f.MergeNode();
        mergeNode1_6.name("mergeNode1");

        InitialNode initialNode3_2 = f.InitialNode();
        initialNode3_2.name("initialNode3");

        ActivityEdge edge9_8 = f.ActivityEdge();
        edge9_8.name("edge9");

        ControlFlow edge10_9 = f.ControlFlow();
        edge10_9.name("edge10");

        ControlFlow edge11_10 = f.ControlFlow();
        edge11_10.name("edge11");

        ActivityEdge edge12_11 = f.ActivityEdge();
        edge12_11.name("edge12");

        ActivityEdge edge13_12 = f.ActivityEdge();
        edge13_12.name("edge13");

        ActivityEdge edge14_13 = f.ActivityEdge();
        edge14_13.name("edge14");

        edge9_8.source(initialNode3_2);
        edge9_8.target(decisionNode1_3);

        edge10_9.source(decisionNode1_3);
        edge10_9.target(action4_4);

        edge10_9.guard(var1_0);

        edge11_10.source(decisionNode1_3);
        edge11_10.target(action5_5);

        edge11_10.guard(var2_1);

        edge12_11.source(action4_4);
        edge12_11.target(mergeNode1_6);

        edge13_12.source(action5_5);
        edge13_12.target(mergeNode1_6);

        edge14_13.source(mergeNode1_6);
        edge14_13.target(finalNode3_7);

        action5_5.outgoing(edge13_12);
        action5_5.incoming(edge11_10);

        action5_5.expressions();

        action4_4.outgoing(edge12_11);
        action4_4.incoming(edge10_9);

        action4_4.expressions();

        decisionNode1_3.outgoing(edge10_9, edge11_10);
        decisionNode1_3.incoming(edge9_8);

        finalNode3_7.outgoing();
        finalNode3_7.incoming(edge14_13);

        mergeNode1_6.outgoing(edge14_13);
        mergeNode1_6.incoming(edge12_11, edge13_12);

        initialNode3_2.outgoing(edge9_8);
        initialNode3_2.incoming();

        Activity test3_14 = f.Activity();
        test3_14.name("test3");
        test3_14.locals(var1_0, var2_1);
        test3_14.inputs();
        test3_14.nodes(action5_5, action4_4, decisionNode1_3, mergeNode1_6, initialNode3_2, finalNode3_7);
        test3_14.edges(edge9_8, edge10_9, edge11_10, edge12_11, edge13_12, edge14_13);

        return test3_14;
    }
}
