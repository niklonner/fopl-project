package bwlag.parse;

import bwlag.model.*;
import model.*;
import parse.*;
import java.util.*;

public class BwlagParser {
    private static TournamentParser parser = new TournamentParser(Arrays.asList("bwlag.model"));

    public static void main(String[] args) throws util.ContextException {
        Tournament<List<Integer>> t = parse(args[0]);
        for (int i=1;i<args.length;i++) {
            t.findSubTournament(args[i]).startBuild();
        }
    }
    
    public static Tournament<List<Integer>> parse(String path) throws util.ContextException {
        return parser.<List<Integer>>parse(path, new RndStandard(), new CmpStandard());
    }
}