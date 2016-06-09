package schemas;

import nl.cwi.managed_data_4j.language.primitives.Primitives;
import schemas.static_diagram.expressions.boolean_expression.operators.BooleanBinaryOperator;
import schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import schemas.static_diagram.expressions.integer_expression.operators.IntegerComparisonOperator;

public interface OperatorsPrimitives extends Primitives {
    BooleanBinaryOperator BooleanBinaryOperator();
    BooleanUnaryOperator BooleanUnaryOperator();

    IntegerCalculationOperator IntegerCalculationOperator();
    IntegerComparisonOperator IntegerComparisonOperator();
}
