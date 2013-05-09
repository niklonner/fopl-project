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
        Player<Integer> p1 = new Player<>("p1");
        Player<Integer> p2 = new Player<>("p2");
        Player<Integer> p3 = new Player<>("p3");
        Player<Integer> p4 = new Player<>("p4");
        Player<Integer> p5 = new Player<>("p5");
        Player<Integer> p6 = new Player<>("p6");
        Player<Integer> p7 = new Player<>("p7");
        Player<Integer> p8 = new Player<>("p8");
        List<Observer> os = Arrays.<Observer>asList(new NodeObserver());
        List<Player<Integer>> players = Arrays.asList(p1,p2,p3,p4,p5,p6,p7);//,p8);
        p7.set("level", 1);
        Bracket.Builder<Integer> b = new Bracket.Builder<>();
        b.groupBy(2).playUntil(2).advance(1).setPlayers(players).setObservers(os);
        Bracket<Integer> br = new Bracket<>(b);
        br.startBuild();
        Iterator<Node<Integer>> it = br.iterator();
        Node<Integer> n = it.next();
    }

    // quick hack.
    private static class NodeObserver implements Observer {
        public void update(Observable o, Object arg) {
            System.out.println("Notification from " + o + ": " + arg);
        }
    }
}
