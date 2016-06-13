package nl.cwi.examples.ql.values;

public class BoolValue extends Value {
	private Boolean value;

	public BoolValue(Boolean v) {
		this.value = v;
	}
	
	public Boolean getValue() {
		return value;
	}
}
