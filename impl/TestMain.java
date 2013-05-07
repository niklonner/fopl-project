import java_cup.runtime.*;
import Swag.*;
import Swag.Absyn.*;
import java.io.*;
import java.util.*;
import generic.*;

public class TestMain
{

    public static void main(String args[]) throws Exception
    {
        //    Swag.Absyn.Prog p = BasicParser.parseTournamentFile(args[0]);
        //    System.out.println("Alright, managed to parse!");
        //p.accept(new generic.GenericVisitor());
        Player<Integer> p1 = new Player<>("Niklas");
        Player<Integer> p2 = new Player<>("Daniel");
        Player<Integer> p3 = new Player<>("Daniel");
        Player<Integer> p4 = new Player<>("Daniel");
        Player<Integer> p5 = new Player<>("Daniel");
        Player<Integer> p6 = new Player<>("Daniel");
        Player<Integer> p7 = new Player<>("Daniel");
        Player<Integer> p8 = new Player<>("Daniel");
        List<Observer> os = Arrays.<Observer>asList(new NodeObserver());
        List<Player<Integer>> players = Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8);// new Play.PlayerParser().parse();
        Bracket.Builder<Integer> b = new Bracket.Builder<>();
        b.groupby(2).playuntil(2).advance(1).setPlayers(players).setObservers(os);
        Bracket<Integer> br = new Bracket<>(b);
        br.startBuild();
    }

    // quick hack.
    private static class NodeObserver implements Observer {
        public void update(Observable o, Object arg) {
            System.out.println(o + ": " + arg);
        }
    }
}
