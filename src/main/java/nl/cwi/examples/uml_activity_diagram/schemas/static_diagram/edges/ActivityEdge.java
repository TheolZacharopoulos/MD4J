package nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.edges;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import nl.cwi.examples.uml_activity_diagram.schemas.NamedElement;
import nl.cwi.examples.uml_activity_diagram.schemas.static_diagram.nodes.ActivityNode;

public interface ActivityEdge extends NamedElement {
	@Inverse(other = ActivityNode.class, field = "outgoing")
	ActivityNode source(ActivityNode...source);

	@Inverse(other = ActivityNode.class, field = "incoming")
	ActivityNode target(ActivityNode...target);
}
