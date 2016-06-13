package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanUnaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public class TestActivity6 {
    
    public static Activity getTestActivity(ActivityFactory f) {

        BooleanValue falseValue = f.BooleanValue();
        falseValue.value(false);

        BooleanVariable notinternal_1 = f.BooleanVariable();
        notinternal_1.name("notinternal");
        notinternal_1.initialValue(falseValue);
        notinternal_1.currentValue(falseValue);

        // TODO: This is input
        BooleanVariable internal_0 = f.BooleanVariable();
        internal_0.name("internal");
        internal_0.initialValue(falseValue);
        internal_0.currentValue(falseValue);

        MergeNode mergeAuthorizePayment_13 = f.MergeNode();
        mergeAuthorizePayment_13.name("mergeAuthorizePayment");

        DecisionNode decisionInternal_4 = f.DecisionNode();
        decisionInternal_4.name("decisionInternal");

        ActivityFinalNode finalNode6_15 = f.ActivityFinalNode();
        finalNode6_15.name("finalNode6");

        InitialNode initialNode6_2 = f.InitialNode();
        initialNode6_2.name("initialNode6");

        OpaqueAction addToWebsite_9 = f.OpaqueAction();
        addToWebsite_9.name("addToWebsite");

        ForkNode forkGetWelcomePackage_7 = f.ForkNode();
        forkGetWelcomePackage_7.name("forkGetWelcomePackage");

        OpaqueAction authorizePayment_14 = f.OpaqueAction();
        authorizePayment_14.name("authorizePayment");

        OpaqueAction managerReport_12 = f.OpaqueAction();
        managerReport_12.name("managerReport");

        OpaqueAction register_3 = f.OpaqueAction();
        register_3.name("register");

        OpaqueAction assignToProjectExternal_5 = f.OpaqueAction();
        assignToProjectExternal_5.name("assignToProjectExternal");

        OpaqueAction assignToProject_8 = f.OpaqueAction();
        assignToProject_8.name("assignToProject");

        JoinNode joinManagerInterview_10 = f.JoinNode();
        joinManagerInterview_10.name("joinManagerInterview");

        OpaqueAction getWelcomePackage_6 = f.OpaqueAction();
        getWelcomePackage_6.name("getWelcomePackage");

        OpaqueAction managerInterview_11 = f.OpaqueAction();
        managerInterview_11.name("managerInterview");


        ActivityEdge edge54_28 = f.ActivityEdge();
        edge54_28.name("edge54");

        ActivityEdge edge42_16 = f.ActivityEdge();
        edge42_16.name("edge42");

        ActivityEdge edge55_29 = f.ActivityEdge();
        edge55_29.name("edge55");

        ActivityEdge edge43_17 = f.ActivityEdge();
        edge43_17.name("edge43");

        ActivityEdge edge56_30 = f.ActivityEdge();
        edge56_30.name("edge56");

        ControlFlow edge44_18 = f.ControlFlow();
        edge44_18.name("edge44");

        ControlFlow edge45_19 = f.ControlFlow();
        edge45_19.name("edge45");

        ActivityEdge edge46_20 = f.ActivityEdge();
        edge46_20.name("edge46");

        ActivityEdge edge47_21 = f.ActivityEdge();
        edge47_21.name("edge47");

        ActivityEdge edge48_22 = f.ActivityEdge();
        edge48_22.name("edge48");

        ActivityEdge edge50_24 = f.ActivityEdge();
        edge50_24.name("edge50");

        ActivityEdge edge49_23 = f.ActivityEdge();
        edge49_23.name("edge49");

        ActivityEdge edge51_25 = f.ActivityEdge();
        edge51_25.name("edge51");

        ActivityEdge edge52_26 = f.ActivityEdge();
        edge52_26.name("edge52");

        ActivityEdge edge53_27 = f.ActivityEdge();
        edge53_27.name("edge53");

        edge54_28.source(mergeAuthorizePayment_13);
        edge54_28.target(authorizePayment_14);


        edge42_16.source(initialNode6_2);
        edge42_16.target(register_3);


        edge55_29.source(authorizePayment_14);
        edge55_29.target(finalNode6_15);


        edge43_17.source(register_3);
        edge43_17.target(decisionInternal_4);


        edge56_30.source(assignToProjectExternal_5);
        edge56_30.target(mergeAuthorizePayment_13);


        edge44_18.source(decisionInternal_4);
        edge44_18.target(assignToProjectExternal_5);

        edge44_18.guard(notinternal_1);

        edge45_19.source(decisionInternal_4);
        edge45_19.target(getWelcomePackage_6);

        edge45_19.guard(internal_0);

        edge46_20.source(getWelcomePackage_6);
        edge46_20.target(forkGetWelcomePackage_7);

        edge47_21.source(forkGetWelcomePackage_7);
        edge47_21.target(assignToProject_8);

        edge48_22.source(forkGetWelcomePackage_7);
        edge48_22.target(addToWebsite_9);

        edge50_24.source(addToWebsite_9);
        edge50_24.target(joinManagerInterview_10);

        edge49_23.source(assignToProject_8);
        edge49_23.target(joinManagerInterview_10);

        edge51_25.source(joinManagerInterview_10);
        edge51_25.target(managerInterview_11);

        edge52_26.source(managerInterview_11);
        edge52_26.target(managerReport_12);

        edge53_27.source(managerReport_12);
        edge53_27.target(mergeAuthorizePayment_13);

        BooleanUnaryExpression exp_0 = f.BooleanUnaryExpression();
        exp_0.assignee(notinternal_1);
        exp_0.operator(BooleanUnaryOperator.NOT);
        exp_0.operand(internal_0);

        mergeAuthorizePayment_13.outgoing(edge54_28);
        mergeAuthorizePayment_13.incoming(edge53_27, edge56_30);

        decisionInternal_4.outgoing(edge44_18, edge45_19);
        decisionInternal_4.incoming(edge43_17);

        finalNode6_15.outgoing();
        finalNode6_15.incoming(edge55_29);

        initialNode6_2.outgoing(edge42_16);
        initialNode6_2.incoming();

        addToWebsite_9.outgoing(edge50_24);
        addToWebsite_9.incoming(edge48_22);

        addToWebsite_9.expressions();

        forkGetWelcomePackage_7.outgoing(edge47_21, edge48_22);
        forkGetWelcomePackage_7.incoming(edge46_20);

        authorizePayment_14.outgoing(edge55_29);
        authorizePayment_14.incoming(edge54_28);

        authorizePayment_14.expressions();

        managerReport_12.outgoing(edge53_27);
        managerReport_12.incoming(edge52_26);

        managerReport_12.expressions();

        register_3.outgoing(edge43_17);
        register_3.incoming(edge42_16);

        register_3.expressions(exp_0);

        assignToProjectExternal_5.outgoing(edge56_30);
        assignToProjectExternal_5.incoming(edge44_18);

        assignToProjectExternal_5.expressions();

        assignToProject_8.outgoing(edge49_23);
        assignToProject_8.incoming(edge47_21);

        assignToProject_8.expressions();

        joinManagerInterview_10.outgoing(edge51_25);
        joinManagerInterview_10.incoming(edge49_23, edge50_24);

        getWelcomePackage_6.outgoing(edge46_20);
        getWelcomePackage_6.incoming(edge45_19);

        getWelcomePackage_6.expressions();

        managerInterview_11.outgoing(edge52_26);
        managerInterview_11.incoming(edge51_25);

        managerInterview_11.expressions();

        Activity test6_31 = f.Activity();
        test6_31.name("test6");
        test6_31.locals(notinternal_1);
        test6_31.inputs(internal_0);
        test6_31.nodes(mergeAuthorizePayment_13, decisionInternal_4, initialNode6_2, addToWebsite_9, forkGetWelcomePackage_7, authorizePayment_14, managerReport_12, register_3, assignToProjectExternal_5, assignToProject_8, joinManagerInterview_10, getWelcomePackage_6, managerInterview_11);
        test6_31.edges(edge54_28, edge42_16, edge55_29, edge43_17, edge56_30, edge44_18, edge45_19, edge46_20, edge47_21, edge48_22, edge50_24, edge49_23, edge51_25, edge52_26, edge53_27);

        finalNode6_15.activity(test6_31);

        return test6_31;
    }
}
