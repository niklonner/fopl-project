package util;

import Play.*;
import Play.Absyn.*;
import generic.Player;

import java_cup.runtime.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PlayerParser {
    /**
     * For simple command-line testing
     */
    public static void main(String args[]) throws Exception {
        System.out.println("\nRunning\n");
        Play.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(args[0]);
        if(parse_tree != null) {
            Worker visitor = new Worker();
            parse_tree.accept(visitor);
        }
    }

    public List<Player> parse(String path) {
        Play.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(path);
        Worker worker = new Worker();
        if(parse_tree != null) {
            parse_tree.accept(worker);
            return worker.getPlayers();
        } else {
            return null;
        }
    }

    private static class Worker implements Visitor {
        List<Player> players = new ArrayList<>();
        Player<Integer> currentPlayer;
        String currentAttribute;

        public List<Player> getPlayers() {
            return players;
        }

        public void visitProg(Play.Absyn.Prog prog) {} //abstract class
        public void visitProgram(Play.Absyn.Program program) {
            if (program.listplayer_ != null) {program.listplayer_.accept(this);}
        }
        public void visitListPlayer(Play.Absyn.ListPlayer listplayer) {
            while(listplayer!= null) {
                listplayer.player_.accept(this);
                listplayer = listplayer.listplayer_;
            }
        }
        public void visitPlayer(Play.Absyn.Player player) {
            currentPlayer = new Player<Integer>(player.string_);
            if (player.listattr_ != null) {player.listattr_.accept(this);}
            players.add(currentPlayer);
        }
        public void visitListAttr(Play.Absyn.ListAttr listattr) {
            while(listattr!= null) {
                listattr.attr_.accept(this);
                listattr = listattr.listattr_;
            }
        }
        public void visitAttr(Play.Absyn.Attr attr) {} //abstract class
        public void visitAttribute(Play.Absyn.Attribute attribute) {
            currentPlayer.set(attribute.ident_, true);
        }
        public void visitValueAttribute(Play.Absyn.ValueAttribute valueattribute) {
            currentAttribute = valueattribute.ident_;
            valueattribute.val_.accept(this);
        }
        public void visitVal(Play.Absyn.Val val) {} //abstract class
        public void visitIntegerValue(Play.Absyn.IntegerValue integervalue) {
            currentPlayer.set(currentAttribute, integervalue.integer_);
        }
        public void visitDoubleValue(Play.Absyn.DoubleValue doublevalue) {
            currentPlayer.set(currentAttribute, doublevalue.double_);
        }
        public void visitStringValue(Play.Absyn.StringValue stringvalue) {
            currentPlayer.set(currentAttribute, stringvalue.string_);
        }
        public void visitIdent(String i) {}
        public void visitInteger(Integer i) {}
        public void visitDouble(Double d) {}
        public void visitChar(Character c) {}
        public void visitString(String s) {}
    }
}
