package generic;

import java.util.*;

public class Tournament<ResultType extends Comparable<? super ResultType>> implements Iterable<SubTournament<ResultType>> {
    public  Map<String,SubTournament<ResultType>> subTournaments;

    public Tournament() {
        subTournaments = new TreeMap<>();
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

    public SubTournament<ResultType> findSubTournament(String str) {
        return subTournaments.get(str);
    }

    public void addSubTournament(String str, SubTournament<ResultType> subTournament) {
        subTournaments.put(str, subTournament);
    }
    
    public Iterator<SubTournament<ResultType>> iterator() {
        //        return subtournaments.iterator();
        return null;
    }
}
