package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.variables;

import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.Value;
import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.values.BooleanValue;

public interface BooleanVariable extends Variable {
    BooleanValue currentValue(BooleanValue...currentValue);

    default Value getCurrentValue() {
        return currentValue();
    }

    default void setCurrentValue(Value value) {
        if (MObjectUtils.instanceOf(value, BooleanValue.class)) {
            BooleanValue booleanValue = (BooleanValue) value;
            currentValue().value(booleanValue.value());
        }
    }
}
