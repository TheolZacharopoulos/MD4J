package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerCalculationExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerComparisonExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerComparisonOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;

public class TestPerformanceVariant3_2 {

    public static Activity testperformance_variant3_2(ActivityFactory f) {

        BooleanValue trueValue = f.BooleanValue();
        trueValue.value(true);

        BooleanValue falseValue = f.BooleanValue();
        falseValue.value(false);

        IntegerValue zeroValue = f.IntegerValue();
        zeroValue.value(0);

        IntegerValue oneValue = f.IntegerValue();
        oneValue.value(1);

        IntegerValue twoValue = f.IntegerValue();
        twoValue.value(2);

        IntegerValue oneFourOneValue = f.IntegerValue();
        oneFourOneValue.value(141);

        IntegerVariable value1_9 = f.IntegerVariable();
        value1_9.name("value1");
        value1_9.initialValue(oneValue);
        value1_9.currentValue(oneValue);

        IntegerVariable value2_1 = f.IntegerVariable();
        value2_1.name("value2");
        value2_1.initialValue(twoValue);
        value2_1.currentValue(twoValue);

        BooleanVariable iL2_4 = f.BooleanVariable();
        iL2_4.name("iL2");
        iL2_4.initialValue(falseValue);
        iL2_4.currentValue(falseValue);

        IntegerVariable loop_5 = f.IntegerVariable();
        loop_5.name("loop");
        loop_5.initialValue(zeroValue);
        loop_5.currentValue(zeroValue);

        BooleanVariable iE2_3 = f.BooleanVariable();
        iE2_3.name("iE2");
        iE2_3.initialValue(falseValue);
        iE2_3.currentValue(falseValue);

        BooleanVariable iG2_2 = f.BooleanVariable();
        iG2_2.name("iG2");
        iG2_2.initialValue(falseValue);
        iG2_2.currentValue(falseValue);

        IntegerVariable iterations_6 = f.IntegerVariable();
        iterations_6.name("iterations");
        iterations_6.initialValue(oneFourOneValue);
        iterations_6.currentValue(oneFourOneValue);

        BooleanVariable loopLiterations_8 = f.BooleanVariable();
        loopLiterations_8.name("loopLiterations");
        loopLiterations_8.initialValue(falseValue);
        loopLiterations_8.currentValue(falseValue);

        BooleanVariable loopEiterations_7 = f.BooleanVariable();
        loopEiterations_7.name("loopEiterations");
        loopEiterations_7.initialValue(falseValue);
        loopEiterations_7.currentValue(falseValue);

        // TODO: Input
        IntegerVariable i_0 = f.IntegerVariable();
        i_0.name("i");
        i_0.initialValue(oneValue);
        i_0.currentValue(oneValue);

        OpaqueAction j_22 = f.OpaqueAction();
        j_22.name("j");

        OpaqueAction k_25 = f.OpaqueAction();
        k_25.name("k");

        OpaqueAction l_26 = f.OpaqueAction();
        l_26.name("l");

        MergeNode mergeE_15 = f.MergeNode();
        mergeE_15.name("mergeE");

        ActivityFinalNode finalNode7_27 = f.ActivityFinalNode();
        finalNode7_27.name("finalNode7");

        InitialNode initialNode7_10 = f.InitialNode();
        initialNode7_10.name("initialNode7");

        MergeNode mergeFinal7_24 = f.MergeNode();
        mergeFinal7_24.name("mergeFinal7");

        DecisionNode decisionLoop_23 = f.DecisionNode();
        decisionLoop_23.name("decisionLoop");

        DecisionNode decisionI_17 = f.DecisionNode();
        decisionI_17.name("decisionI");

        OpaqueAction a_11 = f.OpaqueAction();
        a_11.name("a");

        OpaqueAction b_12 = f.OpaqueAction();
        b_12.name("b");

        OpaqueAction c_13 = f.OpaqueAction();
        c_13.name("c");

        OpaqueAction d_14 = f.OpaqueAction();
        d_14.name("d");

        OpaqueAction e_16 = f.OpaqueAction();
        e_16.name("e");

        OpaqueAction f_18 = f.OpaqueAction();
        f_18.name("f");

        OpaqueAction g_19 = f.OpaqueAction();
        g_19.name("g");

        OpaqueAction h_20 = f.OpaqueAction();
        h_20.name("h");

        OpaqueAction i_21 = f.OpaqueAction();
        i_21.name("i");

        ActivityEdge edge28_34 = f.ActivityEdge();
        edge28_34.name("edge28");

        ControlFlow edge30_36 = f.ControlFlow();
        edge30_36.name("edge30");

        ControlFlow edge29_35 = f.ControlFlow();
        edge29_35.name("edge29");

        ControlFlow edge31_37 = f.ControlFlow();
        edge31_37.name("edge31");

        ActivityEdge edge32_38 = f.ActivityEdge();
        edge32_38.name("edge32");

        ActivityEdge edge33_39 = f.ActivityEdge();
        edge33_39.name("edge33");

        ActivityEdge edge34_40 = f.ActivityEdge();
        edge34_40.name("edge34");

        ActivityEdge edge22_28 = f.ActivityEdge();
        edge22_28.name("edge22");

        ActivityEdge edge35_41 = f.ActivityEdge();
        edge35_41.name("edge35");

        ActivityEdge edge23_29 = f.ActivityEdge();
        edge23_29.name("edge23");

        ActivityEdge edge36_42 = f.ActivityEdge();
        edge36_42.name("edge36");

        ActivityEdge edge24_30 = f.ActivityEdge();
        edge24_30.name("edge24");

        ActivityEdge edge37_43 = f.ActivityEdge();
        edge37_43.name("edge37");

        ActivityEdge edge25_31 = f.ActivityEdge();
        edge25_31.name("edge25");

        ControlFlow edge38_44 = f.ControlFlow();
        edge38_44.name("edge38");

        ControlFlow edge39_45 = f.ControlFlow();
        edge39_45.name("edge39");

        ActivityEdge edge40_46 = f.ActivityEdge();
        edge40_46.name("edge40");

        ActivityEdge edge26_32 = f.ActivityEdge();
        edge26_32.name("edge26");

        ActivityEdge edge41_47 = f.ActivityEdge();
        edge41_47.name("edge41");

        ActivityEdge edge27_33 = f.ActivityEdge();
        edge27_33.name("edge27");

        edge28_34.source(e_16);
        edge28_34.target(decisionI_17);

        edge30_36.source(decisionI_17);
        edge30_36.target(g_19);

        edge30_36.guard(iE2_3);

        edge29_35.source(decisionI_17);
        edge29_35.target(f_18);

        edge29_35.guard(iG2_2);

        edge31_37.source(decisionI_17);
        edge31_37.target(i_21);

        edge31_37.guard(iL2_4);

        edge32_38.source(f_18);
        edge32_38.target(mergeFinal7_24);

        edge33_39.source(mergeFinal7_24);
        edge33_39.target(finalNode7_27);

        edge34_40.source(g_19);
        edge34_40.target(h_20);

        edge22_28.source(initialNode7_10);
        edge22_28.target(a_11);

        edge35_41.source(h_20);
        edge35_41.target(mergeFinal7_24);

        edge23_29.source(a_11);
        edge23_29.target(b_12);

        edge36_42.source(i_21);
        edge36_42.target(j_22);

        edge24_30.source(b_12);
        edge24_30.target(c_13);

        edge37_43.source(j_22);
        edge37_43.target(decisionLoop_23);

        edge25_31.source(c_13);
        edge25_31.target(d_14);

        edge38_44.source(decisionLoop_23);
        edge38_44.target(k_25);

        edge38_44.guard(loopEiterations_7);

        edge39_45.source(decisionLoop_23);
        edge39_45.target(l_26);

        edge39_45.guard(loopLiterations_8);

        edge40_46.source(k_25);
        edge40_46.target(mergeFinal7_24);

        edge26_32.source(d_14);
        edge26_32.target(mergeE_15);

        edge41_47.source(l_26);
        edge41_47.target(mergeE_15);

        edge27_33.source(mergeE_15);
        edge27_33.target(e_16);

        IntegerComparisonExpression exp_int_equals = f.IntegerComparisonExpression();
        exp_int_equals.assignee(loopEiterations_7);
        exp_int_equals.operator(IntegerComparisonOperator.EQUALS);
        exp_int_equals.operand1(loop_5);
        exp_int_equals.operand2(iterations_6);

        IntegerComparisonExpression exp_int_smaller = f.IntegerComparisonExpression();
        exp_int_smaller.assignee(loopLiterations_8);
        exp_int_smaller.operator(IntegerComparisonOperator.SMALLER);
        exp_int_smaller.operand1(loop_5);
        exp_int_smaller.operand2(iterations_6);

        IntegerCalculationExpression exp_int_add = f.IntegerCalculationExpression();
        exp_int_add.assignee(loop_5);
        exp_int_add.operator(IntegerCalculationOperator.ADD);
        exp_int_add.operand1(loop_5);
        exp_int_add.operand2(value1_9);

        IntegerComparisonExpression exp_int_greater = f.IntegerComparisonExpression();
        exp_int_greater.assignee(iG2_2);
        exp_int_greater.operator(IntegerComparisonOperator.GREATER);
        exp_int_greater.operand1(i_0);
        exp_int_greater.operand2(value2_1);

        IntegerComparisonExpression exp_int_equals_2 = f.IntegerComparisonExpression();
        exp_int_equals_2.assignee(iE2_3);
        exp_int_equals_2.operator(IntegerComparisonOperator.EQUALS);
        exp_int_equals_2.operand1(i_0);
        exp_int_equals_2.operand2(value2_1);

        IntegerComparisonExpression exp_int_smaller_2 = f.IntegerComparisonExpression();
        exp_int_smaller_2.assignee(iL2_4);
        exp_int_smaller_2.operator(IntegerComparisonOperator.SMALLER);
        exp_int_smaller_2.operand1(i_0);
        exp_int_smaller_2.operand2(value2_1);

        j_22.outgoing(edge37_43);
        j_22.incoming(edge36_42);

        j_22.expressions(exp_int_equals, exp_int_smaller);

        k_25.outgoing(edge40_46);
        k_25.incoming(edge38_44);

        k_25.expressions();

        l_26.outgoing(edge41_47);
        l_26.incoming(edge39_45);

        l_26.expressions(exp_int_add);

        mergeE_15.outgoing(edge27_33);
        mergeE_15.incoming(edge26_32, edge41_47);

        finalNode7_27.outgoing();
        finalNode7_27.incoming(edge33_39);

        initialNode7_10.outgoing(edge22_28);
        initialNode7_10.incoming();

        mergeFinal7_24.outgoing(edge33_39);
        mergeFinal7_24.incoming(edge32_38, edge35_41, edge40_46);

        decisionLoop_23.outgoing(edge38_44, edge39_45);
        decisionLoop_23.incoming(edge37_43);

        decisionI_17.outgoing(edge29_35, edge30_36, edge31_37);
        decisionI_17.incoming(edge28_34);

        a_11.outgoing(edge23_29);
        a_11.incoming(edge22_28);

        a_11.expressions(exp_int_greater, exp_int_equals_2, exp_int_smaller_2);

        b_12.outgoing(edge24_30);
        b_12.incoming(edge23_29);

        b_12.expressions();

        c_13.outgoing(edge25_31);
        c_13.incoming(edge24_30);

        c_13.expressions();

        d_14.outgoing(edge26_32);
        d_14.incoming(edge25_31);

        d_14.expressions();

        e_16.outgoing(edge28_34);
        e_16.incoming(edge27_33);

        e_16.expressions();

        f_18.outgoing(edge32_38);
        f_18.incoming(edge29_35);

        f_18.expressions();

        g_19.outgoing(edge34_40);
        g_19.incoming(edge30_36);

        g_19.expressions();

        h_20.outgoing(edge35_41);
        h_20.incoming(edge34_40);

        h_20.expressions();

        i_21.outgoing(edge36_42);
        i_21.incoming(edge31_37);

        i_21.expressions();

        Activity testperformance_variant3_2_48 = f.Activity();
        testperformance_variant3_2_48.name("testperformance_variant3_2");
        testperformance_variant3_2_48.locals(value1_9, value2_1, iL2_4, loop_5, iE2_3, iG2_2, iterations_6, loopLiterations_8, loopEiterations_7);
        testperformance_variant3_2_48.inputs(i_0);
        testperformance_variant3_2_48.nodes(
                j_22, k_25, l_26, mergeE_15, initialNode7_10, mergeFinal7_24, decisionLoop_23, decisionI_17, a_11, b_12,
                c_13, d_14, e_16, f_18, g_19, h_20, i_21, finalNode7_27);

        testperformance_variant3_2_48.edges(edge28_34, edge30_36, edge29_35, edge31_37, edge32_38, edge33_39, edge34_40,
                edge22_28, edge35_41, edge23_29, edge36_42, edge24_30, edge37_43, edge25_31, edge38_44, edge39_45,
                edge40_46, edge26_32, edge41_47, edge27_33);

        return testperformance_variant3_2_48;
    }

}
