package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanUnaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public interface BooleanUnaryExpression extends BooleanExpression {
	BooleanVariable operand(BooleanVariable... operant);
	BooleanUnaryOperator operator(BooleanUnaryOperator... operator);

	default void execute() {
		boolean operandValue = getCurrentBoolValue(operand());

		Boolean result = null;
		switch (operator()) {
			case NOT:
				result = !operandValue;
				break;
		}
		assignValue(result);
	}
}
