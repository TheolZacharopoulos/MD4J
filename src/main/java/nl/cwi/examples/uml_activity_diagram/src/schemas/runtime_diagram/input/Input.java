package schemas.runtime_diagram.input;

import nl.cwi.managed_data_4j.M;

import java.util.List;

public interface Input extends M {
	List<InputValue> inputValues(InputValue...inputValues);
}