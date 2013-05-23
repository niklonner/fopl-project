package bwlag.model;

import java.util.*;

public class RndStandard implements model.RandomGenerator<List<Integer>> {
    private static Random rnd = new Random();
    private static final int NUM_GAMES = 6;

    public List<Integer> next() {
        List<Integer> ls = new LinkedList<>();
        for (int i=0;i<NUM_GAMES;i++) {
            ls.add(rnd.nextInt(201)+100); // number between 100 and 300
        }
        return ls;
    }
}