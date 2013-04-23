package generic;

import java.util.*;

public class Tournament implements Iterable<Node> {
    private Map<String, Node> nodes;
    
    public Tournament() {
	nodes = new HashMap<String, Node>();
    }

    // returns true if add is ok (name doesnt already exist)
    public boolean addNode(Node n) {
	if (!nodes.containsKey(n.getName())) {
	    nodes.put(n.getName(),n);
	    return true;
	} else {
	    return false;
	}
    }

    public Iterator<Node> iterator() {
	return nodes.values().iterator();
    }
}