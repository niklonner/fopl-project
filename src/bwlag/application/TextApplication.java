package bwlag.application;

import bwlag.parse.*;
import model.*;
import util.*;

import java.util.*;
import java.io.*;

/* Program that interactively asks the user to input results, and shows updates (advancements of players) */

public class TextApplication {
    public static void main(String[] args) throws IOException {
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
        
        public void startDialogue() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("When prompted, enter a whitespace-separated list of games.");
            for (SubTournament<List<Integer>> st : tournament) {
                System.out.println("Now at subtournament " + st.getName());
                for (Node<List<Integer>> n : st) {
                    System.out.println("Now at node " + n.getId());
                    for (Player<List<Integer>> p : n.getPlayers()) {
                        System.out.print("Enter score for " + p.getName() + ": ");
                        List<Integer> score = new LinkedList<>();
                        String scoreLine = br.readLine();
                        Scanner sc = new Scanner(scoreLine);
                        while (sc.hasNextInt()) {
                            score.add(sc.nextInt());
                        }
                        n.addResult(p.getId(),score);
                    }
                }
            }
        }
        
        public void update(Observable o, Object arg) {
            if (arg instanceof Pair<?,?>) {
                switch (((Pair<EventType,?>)arg).fst) {
                case SENDOFF:
                    Node<List<Integer>> node = (Node<List<Integer>>) o;
                    List<Pair<Player<List<Integer>>,PlayerReceiver<List<Integer>>>> ls =
                        ((Pair<?,List<Pair<Player<List<Integer>>,PlayerReceiver<List<Integer>>>>>)arg).snd;
                    System.out.printf("node %d sending players off:\n", node.getId());
                    SortedSet<Player<List<Integer>>> players = new TreeSet(node.getPlayers().comparator());
                    for (Pair<Player<List<Integer>>,PlayerReceiver<List<Integer>>> pair : ls) {
                        players.add(pair.fst);
                    }
                    for (Player<?> p : players) {
                        System.out.println("\t" + p);
                    }
                    for (Pair<Player<List<Integer>>,PlayerReceiver<List<Integer>>> pair : ls) {
                        String type;
                        if (pair.snd instanceof SubTournament<?>) {
                            type = "subtournament";
                        } else if (pair.snd instanceof Node<?> ){
                            type = "node";
                        } else {
                            System.out.printf("not sending player %s anywhere\n", pair.fst.getName());
                            continue;
                        }
                        System.out.printf("sending player %s to %s %d\n",pair.fst.getName(),type, pair.snd.getId());
                    }
                    break;
                case UPDATE:
                    // something has changed about the node - fetch information and update
                    // we do not want to do anything, however
                    break;
                default:
                    break;
                }
            }
        }
    }
}