package schemas.static_diagram.expressions.integer_expression;

import helpers.FactoriesProvider;
import schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import schemas.static_diagram.values.IntegerValue;
import schemas.static_diagram.variables.IntegerVariable;

public interface IntegerCalculationExpression extends IntegerExpression {
	IntegerVariable assignee(IntegerVariable... assignee);
	IntegerCalculationOperator operator(IntegerCalculationOperator... operator);

	@Override
	default void execute() {
		int operandValue1 = getCurrentIntValue(operand1());
		int operandValue2 = getCurrentIntValue(operand2());

		Integer result = null;
		switch(operator()) {
			case ADD:
				result = operandValue1 + operandValue2;
				break;
			case SUBRACT:
				result = operandValue1 - operandValue2;
				break;
		}
		IntegerValue resultValue = FactoriesProvider.getValuesFactory().IntegerValue();

		resultValue.value(result);
		assignee().currentValue(resultValue);
	}
}
