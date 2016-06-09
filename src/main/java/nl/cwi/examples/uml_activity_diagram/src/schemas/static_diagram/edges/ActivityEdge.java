package schemas.static_diagram.edges;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import schemas.NamedElement;
import schemas.static_diagram.nodes.ActivityNode;

public interface ActivityEdge extends NamedElement {
	@Inverse(other = ActivityNode.class, field = "outgoing")
	ActivityNode source(ActivityNode...source);

	@Inverse(other = ActivityNode.class, field = "incoming")
	ActivityNode target(ActivityNode...target);
}
