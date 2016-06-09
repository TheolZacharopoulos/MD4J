package schemas.static_diagram.expressions.boolean_expression;

import schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import schemas.static_diagram.variables.BooleanVariable;

public interface BooleanUnaryExpression extends BooleanExpression {
	BooleanVariable operant(BooleanVariable... operant);
	BooleanUnaryOperator operator(BooleanUnaryOperator... operator);

	default void execute() {
		boolean operandValue = getCurrentBoolValue(operant());

		Boolean result = null;
		switch (operator()) {
			case NOT:
				result = !operandValue;
				break;
		}
		assignValue(result);
	}
}
