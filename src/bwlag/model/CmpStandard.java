package bwlag.model;

import model.*;
import java.util.*;

/* Standard comparator for bowling scores.
 */

public class CmpStandard implements Comparator<Player<List<Integer>>>{

    public int compare(Player<List<Integer>> p1, Player<List<Integer>> p2) {
        boolean p1set = p1.resultIsSet();
        boolean p2set = p2.resultIsSet();
        int tiebreaker = p1.getId() - p2.getId();
        if (!p1set) {
            return p2set ? 1 : tiebreaker;
        } else if (!p2set) {
            return p1set ? -1 : tiebreaker;
        } else {
            int score1 = 0;
            if (p1set) {
                for (Integer i : (List<Integer>)p1.getResult()) {
                    score1 += i;
                }
            }
            int score2 = 0;
            if (p2set) {
                for (Integer i : (List<Integer>)p2.getResult()) {
                    score2 += i;
                }
            }
            int res = score1-score2;
            return res != 0 ? -res : tiebreaker;
        }
    }
    
}