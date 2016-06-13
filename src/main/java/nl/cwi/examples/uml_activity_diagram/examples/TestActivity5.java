package nl.cwi.examples.uml_activity_diagram.examples;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.Activity;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity.ActivityFactory;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerCalculationExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityFinalNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.InitialNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.OpaqueAction;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;

public class TestActivity5 {
    
    public static Activity getTestActivity(ActivityFactory f) {

        IntegerValue zeroValue = f.IntegerValue();
        zeroValue.value(0);

        IntegerValue fiveValue = f.IntegerValue();
        fiveValue.value(1);

        IntegerValue tenValue = f.IntegerValue();
        tenValue.value(10);

        IntegerVariable var10_1 = f.IntegerVariable();
        var10_1.name("var10");
        var10_1.initialValue(fiveValue);
        var10_1.currentValue(fiveValue);

        IntegerVariable var11_2 = f.IntegerVariable();
        var11_2.name("var11");
        var11_2.initialValue(zeroValue);
        var11_2.currentValue(zeroValue);

        // TODO: This is input
        IntegerVariable var9_0 = f.IntegerVariable();
        var9_0.name("var9");
        var9_0.initialValue(tenValue);
        var9_0.currentValue(tenValue);

        ActivityFinalNode finalNode5_5 = f.ActivityFinalNode();
        finalNode5_5.name("finalNode5");

        InitialNode initialNode5_3 = f.InitialNode();
        initialNode5_3.name("initialNode5");

        OpaqueAction action10_4 = f.OpaqueAction();
        action10_4.name("action10");

        ActivityEdge edge20_6 = f.ActivityEdge();
        edge20_6.name("edge20");

        ActivityEdge edge21_7 = f.ActivityEdge();
        edge21_7.name("edge21");

        edge20_6.source(initialNode5_3);
        edge20_6.target(action10_4);

        edge21_7.source(action10_4);
        edge21_7.target(finalNode5_5);

        IntegerCalculationExpression exp_0 = f.IntegerCalculationExpression();
        exp_0.assignee(var11_2);
        exp_0.operator(IntegerCalculationOperator.ADD);
        exp_0.operand1(var9_0);
        exp_0.operand2(var10_1);

        finalNode5_5.outgoing();
        finalNode5_5.incoming(edge21_7);

        initialNode5_3.outgoing(edge20_6);
        initialNode5_3.incoming();

        action10_4.outgoing(edge21_7);
        action10_4.incoming(edge20_6);

        action10_4.expressions(exp_0);

        Activity test5_8 = f.Activity();
        test5_8.name("test5");
        test5_8.locals(var10_1, var11_2);
        test5_8.inputs(var9_0);
        test5_8.nodes(initialNode5_3, action10_4, finalNode5_5);
        test5_8.edges(edge20_6, edge21_7);

        return test5_8;
    }
}
