package schemas.static_diagram.expressions.integer_expression;

import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import schemas.static_diagram.expressions.Expression;
import schemas.static_diagram.values.IntegerValue;
import schemas.static_diagram.values.Value;
import schemas.static_diagram.variables.IntegerVariable;
import schemas.static_diagram.variables.Variable;

public interface IntegerExpression extends Expression {
	IntegerVariable operand1(IntegerVariable...operand1);
	IntegerVariable operand2(IntegerVariable...operand2);

	default Integer getCurrentIntValue(Variable variable) {
		Integer currentValue = null;
		Value value = variable.currentValue();

		if (MObjectUtils.instanceOf(value, IntegerValue.class)) {
			IntegerValue integerValue = (IntegerValue) value;
			currentValue = integerValue.value();
		}
		return currentValue;
	}
}
