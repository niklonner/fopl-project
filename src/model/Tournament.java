package model;

import java.util.*;
import sets.*;
import parse.*;

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
