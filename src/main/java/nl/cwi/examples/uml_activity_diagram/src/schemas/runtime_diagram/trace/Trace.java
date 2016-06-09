package schemas.runtime_diagram.trace;

import nl.cwi.managed_data_4j.M;
import schemas.static_diagram.nodes.ActivityNode;

import java.util.List;

public interface Trace extends M {
	List<ActivityNode> executedNodes(ActivityNode...nodes);
}
