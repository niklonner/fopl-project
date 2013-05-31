package bwlag.parse;

import bwlag.model.*;
import model.*;
import parse.*;
import java.util.*;

/* Parser for bwlag files. Simply inputs a RandomGenerator, PrettyPrinterScore and Comparator to the
 * general TournamentParser.
 */

public class BwlagParser {
    private static TournamentParser parser = new TournamentParser(Arrays.asList("bwlag.model"));

    public static void main(String[] args) throws util.ContextException, java.io.FileNotFoundException {
        Tournament<List<Integer>> t = parse(args[0]);
    }
    
    public static Tournament<List<Integer>> parse(String path) throws util.ContextException, java.io.FileNotFoundException {
        return parser.<List<Integer>>parse(path, new RndStandard(), new CmpStandard(), new PPSStandard());
    }
}