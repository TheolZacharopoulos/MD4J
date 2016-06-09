package schemas.static_diagram.variables;

import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import schemas.static_diagram.values.BooleanValue;
import schemas.static_diagram.values.Value;

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
