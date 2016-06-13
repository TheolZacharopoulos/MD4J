package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.integer_expression;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.IntegerValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.Variable;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.IntegerVariable;

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
