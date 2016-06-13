package nl.cwi.examples.ql.values;

public class IntValue extends Value {
	private Integer value;

	public IntValue(Integer v) {
		this.value = v;
	}
	
	public Integer getValue() {
		return value;
	}
}
