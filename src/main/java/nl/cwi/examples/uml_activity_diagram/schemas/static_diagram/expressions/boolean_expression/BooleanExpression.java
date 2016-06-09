package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.boolean_expression;

import nl.cwi.examples.uml_activity_diagram.helpers.FactoriesProvider;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.expressions.Expression;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.Variable;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables.BooleanVariable;

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
