package model;

import java.util.*;
import sets.*;
import parse.*;

/* A tournament is simply a sequence of subtournaments. This class glues them together
 * and provides lookup by name and iteration. Note that iteration order is the same
 * as the order in which the subtournaments were added.
 */

public class Tournament<ResultType> implements Iterable<SubTournament<ResultType>> {
    public  LinkedHashMap<String,SubTournament<ResultType>> subTournaments;

    public Tournament() {
        subTournaments = new LinkedHashMap<>();
    }

    public SubTournament<ResultType> findSubTournament(String str) {
        return subTournaments.get(str);
    }

    public void addSubTournament(String str, SubTournament<ResultType> subTournament) {
        subTournaments.put(str, subTournament);
    }
    
    public Iterator<SubTournament<ResultType>> iterator() {
        return subTournaments.values().iterator();
    }
}
