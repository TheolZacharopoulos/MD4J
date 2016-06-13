package nl.cwi.examples.uml_activity_diagram.schemas;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerComparisonOperator;
import nl.cwi.managed_data_4j.language.primitives.Primitives;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanBinaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;

public interface OperatorsPrimitives extends Primitives {
    BooleanBinaryOperator BooleanBinaryOperator();
    BooleanUnaryOperator BooleanUnaryOperator();

    IntegerCalculationOperator IntegerCalculationOperator();
    IntegerComparisonOperator IntegerComparisonOperator();
}
