package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanBinaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanUnaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanBinaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerCalculationExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerComparisonExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerComparisonOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityFinalNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.InitialNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.OpaqueAction;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;

public class TestActivity4 {

    public static Activity getActivity(ActivityFactory f) {

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

        IntegerVariable var4_1 = f.IntegerVariable();
        var4_1.name("var4");
        var4_1.initialValue(oneValue);

        BooleanVariable var7_4 = f.BooleanVariable();
        var7_4.name("var7");
        var7_4.initialValue(trueValue);

        IntegerVariable var5_2 = f.IntegerVariable();
        var5_2.name("var5");
        var5_2.initialValue(twoValue);

        BooleanVariable var8_5 = f.BooleanVariable();
        var8_5.name("var8");
        var8_5.initialValue(falseValue);

        IntegerVariable var3_0 = f.IntegerVariable();
        var3_0.name("var3");
        var3_0.initialValue(zeroValue);

        BooleanVariable var6_3 = f.BooleanVariable();
        var6_3.name("var6");
        var6_3.initialValue(falseValue);

        OpaqueAction action6_7 = f.OpaqueAction();
        action6_7.name("action6");

        ActivityFinalNode finalNode4_11 = f.ActivityFinalNode();
        finalNode4_11.name("finalNode4");

        InitialNode initialNode4_6 = f.InitialNode();
        initialNode4_6.name("initialNode4");

        OpaqueAction action9_10 = f.OpaqueAction();
        action9_10.name("action9");

        OpaqueAction action8_9 = f.OpaqueAction();
        action8_9.name("action8");

        OpaqueAction action7_8 = f.OpaqueAction();
        action7_8.name("action7");

        ActivityEdge edge16_13 = f.ActivityEdge();
        edge16_13.name("edge16");

        ActivityEdge edge17_14 = f.ActivityEdge();
        edge17_14.name("edge17");

        ActivityEdge edge18_15 = f.ActivityEdge();
        edge18_15.name("edge18");

        ActivityEdge edge19_16 = f.ActivityEdge();
        edge19_16.name("edge19");

        ActivityEdge edge15_12 = f.ActivityEdge();
        edge15_12.name("edge15");

        edge16_13.source(action6_7);
        edge16_13.target(action7_8);

        edge17_14.source(action7_8);
        edge17_14.target(action8_9);

        edge18_15.source(action8_9);
        edge18_15.target(action9_10);

        edge19_16.source(action9_10);
        edge19_16.target(finalNode4_11);

        edge15_12.source(initialNode4_6);
        edge15_12.target(action6_7);

        IntegerCalculationExpression exp_int_add = f.IntegerCalculationExpression();
        exp_int_add.assignee(var3_0);
        exp_int_add.operator(IntegerCalculationOperator.ADD);
        exp_int_add.operand1(var3_0);
        exp_int_add.operand2(var4_1);

        IntegerCalculationExpression exp_int_add_2 = f.IntegerCalculationExpression();
        exp_int_add_2.assignee(var3_0);
        exp_int_add_2.operator(IntegerCalculationOperator.ADD);
        exp_int_add_2.operand1(var3_0);
        exp_int_add_2.operand2(var5_2);

        BooleanBinaryExpression exp_bool_or = f.BooleanBinaryExpression();
        exp_bool_or.assignee(var8_5);
        exp_bool_or.operator(BooleanBinaryOperator.OR);
        exp_bool_or.operand1(var6_3);
        exp_bool_or.operand2(var7_4);

        BooleanUnaryExpression exp_bool_not = f.BooleanUnaryExpression();
        exp_bool_not.assignee(var7_4);
        exp_bool_not.operator(BooleanUnaryOperator.NOT);
        exp_bool_not.operand(var6_3);

        IntegerComparisonExpression exp_int_smaller = f.IntegerComparisonExpression();
        exp_int_smaller.assignee(var6_3);
        exp_int_smaller.operator(IntegerComparisonOperator.SMALLER);
        exp_int_smaller.operand1(var4_1);
        exp_int_smaller.operand2(var5_2);

        action6_7.outgoing(edge16_13);
        action6_7.incoming(edge15_12);

        action6_7.expressions(exp_int_add, exp_int_add_2);

        finalNode4_11.outgoing();
        finalNode4_11.incoming(edge19_16);

        initialNode4_6.outgoing(edge15_12);
        initialNode4_6.incoming();

        action9_10.outgoing(edge19_16);
        action9_10.incoming(edge18_15);

        action9_10.expressions(exp_bool_or);

        action8_9.outgoing(edge18_15);
        action8_9.incoming(edge17_14);

        action8_9.expressions(exp_bool_not);

        action7_8.outgoing(edge17_14);
        action7_8.incoming(edge16_13);

        action7_8.expressions(exp_int_smaller);

        Activity test4_17 = f.Activity();
        test4_17.name("test4");
        test4_17.locals(var4_1, var7_4, var5_2, var8_5, var3_0, var6_3);
        test4_17.inputs();
        test4_17.nodes(action6_7, initialNode4_6, action9_10, action8_9, action7_8, finalNode4_11);
        test4_17.edges(edge16_13, edge17_14, edge18_15, edge19_16, edge15_12);

        return test4_17;
    }
}
