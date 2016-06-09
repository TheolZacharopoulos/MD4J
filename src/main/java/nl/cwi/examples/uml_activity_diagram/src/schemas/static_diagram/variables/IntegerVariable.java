package schemas.static_diagram.variables;

import nl.cwi.managed_data_4j.language.utils.MObjectUtils;
import schemas.static_diagram.values.IntegerValue;
import schemas.static_diagram.values.Value;

public interface IntegerVariable extends Variable {
    IntegerValue currentValue(IntegerValue...currentValue);

    default Value getCurrentValue() {
        return currentValue();
    }

    default void setCurrentValue(Value value) {
        if (MObjectUtils.instanceOf(value, IntegerValue.class)) {
            IntegerValue integerValue = (IntegerValue) value;
            currentValue().value(integerValue.value());
        }
    }
}
