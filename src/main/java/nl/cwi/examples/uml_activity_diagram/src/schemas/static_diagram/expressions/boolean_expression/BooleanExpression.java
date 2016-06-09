package schemas.static_diagram.expressions.boolean_expression;

import helpers.FactoriesProvider;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import schemas.static_diagram.expressions.Expression;
import schemas.static_diagram.values.BooleanValue;
import schemas.static_diagram.values.Value;
import schemas.static_diagram.variables.BooleanVariable;
import schemas.static_diagram.variables.Variable;

public interface BooleanExpression extends Expression {
	BooleanVariable assignee(BooleanVariable... assignee);

	default BooleanVariable getAssignee() {
		return assignee();
	}

	default Boolean getCurrentBoolValue(Variable variable) {
		Boolean currentValue = null;
		Value value = variable.currentValue();

		if (MObjectUtils.instanceOf(value, BooleanValue.class)) {
			BooleanValue booleanValue = (BooleanValue) value;
			currentValue = booleanValue.value();
		}
		return currentValue;
	}

	default void assignValue(Boolean value) {
		BooleanValue resultValue = FactoriesProvider.getValuesFactory().BooleanValue();
		resultValue.value(value);
		getAssignee().currentValue(resultValue);
	}
}
