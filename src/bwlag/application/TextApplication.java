package bwlag.application;

import bwlag.parse.*;
import model.*;
import util.*;

import java.util.*;

public class TextApplication {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: TextApplication sourcepath");
            System.exit(-1);
        }
        Tournament<List<Integer>> tournament = null;
        try {
            tournament = BwlagParser.parse(args[0]);
        } catch (ContextException e) {
            System.err.println("Error when parsing:");
            e.printStackTrace();
            System.exit(-1);
        } catch (java.io.FileNotFoundException e) {
            System.err.println("File " + args[0] + " not found.");
            System.exit(-1);
        }
        BwlagObserver bo = new BwlagObserver(tournament);
        for (SubTournament<List<Integer>> st : tournament) {
            st.addObserver(bo);
        }
        bo.startDialogue();
    }

    private static class BwlagObserver implements Observer {
        Tournament<List<Integer>> tournament;

        public BwlagObserver(Tournament<List<Integer>> tournament) {
            this.tournament = tournament;
        }
        
        public void startDialogue() {
            for (SubTournament<List<Integer>> st : tournament) {
                System.out.println("Now at subtournament " + st.getName());
                for (Node<List<Integer>> n : st) {
                    System.out.println(n.getId());
                    for (Player<List<Integer>> p : n.getPlayers()) {
                        System.out.println("Player " + p.getName());
                    }
                }
            }
        }
        
        public void update(Observable o, Object arg) {
            
        }
    }
}