package bwlag.model;

import java.util.*;
import model.*;

/* Standard pretty-printer */

public class PPSStandard implements model.PrettyPrinterScore<List<Integer>> {
    public String prettyPrint(Player<List<Integer>> p) {
        List<Integer> ls = p.resultIsSet() ? p.getResult() : null;
        if (ls==null) {
            return "Player " + p.getId() + " 0 points";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Player ");
        sb.append(p.getName());
        sb.append(" games ");
        int score = 0;
        for (Integer i : ls) {
            sb.append(i);
            sb.append(" ");
            score += i;
        }
        sb.append("total ");
        sb.append(score);
        return sb.toString();
    }
}