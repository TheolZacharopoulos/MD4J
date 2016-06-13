package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.activity;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.input.InputValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ActivityEdge;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges.ControlFlow;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanBinaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.BooleanUnaryExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerCalculationExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerComparisonExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.IntegerExpression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.*;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;
import nl.cwi.managed_data_4j.IFactory;

public interface ActivityFactory extends IFactory {
    // Activity
    Activity Activity();

    // Edges
    ActivityEdge ActivityEdge();
    ControlFlow ControlFlow();

    // Nodes
    InitialNode InitialNode();
    FinalNode FinalNode();
    DecisionNode DecisionNode();
    ForkNode ForkNode();
    JoinNode JoinNode();
    MergeNode MergeNode();
    ActivityFinalNode ActivityFinalNode();
    Action Action();
    OpaqueAction OpaqueAction();

    // Values
    IntegerValue IntegerValue();
    BooleanValue BooleanValue();
    InputValue InputValue();

    // Variables
    IntegerVariable IntegerVariable();
    BooleanVariable BooleanVariable();

    // Expressions
    Expression Expression();
    BooleanExpression BooleanExpression();
    BooleanBinaryExpression BooleanBinaryExpression();
    BooleanUnaryExpression BooleanUnaryExpression();
    IntegerExpression IntegerExpression();
    IntegerCalculationExpression IntegerCalculationExpression();
    IntegerComparisonExpression IntegerComparisonExpression();
}
