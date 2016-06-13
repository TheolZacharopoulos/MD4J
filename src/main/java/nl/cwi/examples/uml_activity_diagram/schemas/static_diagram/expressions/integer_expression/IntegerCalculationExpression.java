package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression.operators.IntegerCalculationOperator;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;

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
		IntegerValue resultValue = FactoriesProvider.getActivityDiagramFactory().IntegerValue();

		resultValue.value(result);
		assignee().currentValue(resultValue);
	}
}
