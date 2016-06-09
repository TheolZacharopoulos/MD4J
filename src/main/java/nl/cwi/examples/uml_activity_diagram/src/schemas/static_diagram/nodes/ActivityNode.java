package schemas.static_diagram.nodes;

import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Inverse;
import schemas.NamedElement;
import schemas.static_diagram.activity.Activity;
import schemas.static_diagram.edges.ActivityEdge;

import java.util.List;

public interface ActivityNode extends NamedElement {
	@Contain
	@Inverse(other = ActivityEdge.class, field = "source")
	List<ActivityEdge> outgoing(ActivityEdge...outgoing);

	@Contain
	@Inverse(other = ActivityEdge.class, field = "target")
	List<ActivityEdge> incoming(ActivityEdge...incoming);

	@Contain
	@Inverse(other = Activity.class, field = "nodes")
	Activity activity(Activity... activity);
}
