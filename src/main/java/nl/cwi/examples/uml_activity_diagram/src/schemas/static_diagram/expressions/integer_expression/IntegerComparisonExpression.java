package schemas.static_diagram.expressions.integer_expression;

import helpers.FactoriesProvider;
import schemas.static_diagram.expressions.integer_expression.operators.IntegerComparisonOperator;
import schemas.static_diagram.values.BooleanValue;
import schemas.static_diagram.variables.BooleanVariable;

public interface IntegerComparisonExpression extends IntegerExpression {
	BooleanVariable assignee(BooleanVariable... assignee);
	IntegerComparisonOperator operator();

	@Override
	default void execute() {
		int operandValue1 = getCurrentIntValue(operand1());
		int operandValue2 = getCurrentIntValue(operand2());

		Boolean result = null;
		switch(operator()) {
			case EQUALS:
				result = operandValue1 == operandValue2;
				break;
			case GREATER:
				result = operandValue1 > operandValue2;
				break;
			case GREATER_EQUALS:
				result = operandValue1 >= operandValue2;
				break;
			case SMALLER:
				result = operandValue1 < operandValue2;
				break;
			case SMALLER_EQUALS:
				result = operandValue1 <= operandValue2;
				break;
		}
		BooleanValue resultValue = FactoriesProvider.getValuesFactory().BooleanValue();

		resultValue.value(result);
		assignee().currentValue(resultValue);
	}
}
