package nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.tokens;

import nl.cwi.examples.uml_activity_diagram.schemas.runtime_diagram.runtime_nodes.RuntimeActivityNode;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;
import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;

public interface Token extends M {

	@Inverse(other = ActivityNode.class, field = "heldTokens")
	RuntimeActivityNode holder(ActivityNode...holder);

	default Token transfer(ActivityNode holder) {
		if (this.holder() != null) {
			this.withdraw();
		}
		this.holder(holder);
		return this;
	}

	default void withdraw() {
		if (!isWithdrawn()) {
			holder().removeToken(this);
			holder(null);
		}
	}

	default boolean isWithdrawn() {
		return this.holder() == null;
	}
}
