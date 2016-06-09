package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression.operators.BooleanBinaryOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

public interface BooleanBinaryExpression extends BooleanExpression {
	BooleanVariable operand1(BooleanVariable... operant1);
	BooleanVariable operand2(BooleanVariable... operant2);
	BooleanBinaryOperator operator(BooleanBinaryOperator... operator);

	default void execute() {
		boolean operandValue1 = getCurrentBoolValue(operand1());
		boolean operandValue2 = getCurrentBoolValue(operand2());

		Boolean result = null;
		switch (operator()) {
			case AND:
				result = operandValue1 && operandValue2;
				break;
			case OR:
				result = operandValue1 || operandValue2;
				break;
		}
		assignValue(result);
	}
}
