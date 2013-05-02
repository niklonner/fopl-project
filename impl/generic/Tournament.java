package generic;

import java.util.*;

public class Tournament implements Iterable<SubTournament<?>> {
    private List<SubTournament<?>> subtournaments;

    public Tournament() {
        subtournaments = new ArrayList<>();
    }

    // returns true if add is ok (name doesnt already exist)
    // public boolean addNode(Node n) {
    // 	if (!nodes.containsKey(n.getName())) {
    // 	    nodes.put(n.getName(),n);
    // 	    return true;
    // 	} else {
    // 	    return false;
    // 	}
    // }

    public Iterator<SubTournament<?>> iterator() {
        return subtournaments.iterator();
    }
}
