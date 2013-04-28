package Play;

import Play.*;
import Play.Absyn.*;
import generic.Player;

import java_cup.runtime.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PlayerParser {
    /**
     * For command-line testing
     */
    public static void main(String args[]) throws Exception {
        List<Player> players = new ArrayList<>();
        Yylex l = null;
        parser p;
        try {
            if (args.length == 0) l = new Yylex(System.in);
            else l = new Yylex(new FileReader(args[0]));
        }
        catch(FileNotFoundException e) {
            System.err.println("Error: File not found: " + args[0]);
            System.exit(1);
        }
        p = new parser(l);
        /* The default parser is the first-defined entry point. */
        /* You may want to change this. Other options are: */
        /* pListPlayer, pPlayer, pListAttr, pAttr, pVal */
        try {
            Play.Absyn.Prog parse_tree = p.pProg();
            System.out.println();
            System.out.println("Parse Succesful!");
            System.out.println();
            System.out.println("[Abstract Syntax]");
            System.out.println();
            System.out.println(PrettyPrinter.show(parse_tree));
            System.out.println();
            System.out.println("[Linearized Tree]");
            System.out.println();
            System.out.println(PrettyPrinter.print(parse_tree));

            System.out.println();
            System.out.println("[Parsing output]");
            System.out.println();

            Parser parser = new Parser();
            parse_tree.accept(parser);
            players = parser.getPlayers();
        } catch(Throwable e) {
            System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        }
    }

    public List<Player> parse(String path) {
        List<Player> players = new ArrayList<>();
        Yylex l = null;
        parser p;

        try {
            l = new Yylex(new FileReader(path));
        }
        catch(FileNotFoundException e) {
            System.err.println("Error: File not found: " + path);
            System.exit(1);
        }

        p = new parser(l);

        try {
            Play.Absyn.Prog parse_tree = p.pProg();

            Parser parser = new Parser();
            parse_tree.accept(parser);
            players = parser.getPlayers();
        } catch(Throwable e) {
            System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Number of player objects: " + players.size());

        return players;
    }

    private static class Parser implements Visitor {
        List<Player> players = new ArrayList<>();
        Player<Integer> currentPlayer;
        String currentAttribute;

        public List<Player> getPlayers() {
            return players;
        }

        public void visitProg(Play.Absyn.Prog prog) {} //abstract class
        public void visitProgram(Play.Absyn.Program program) {
            /* Code For Program Goes Here */

            System.out.println("Visiting Program");

            if (program.listplayer_ != null) {program.listplayer_.accept(this);}
        }
        public void visitListPlayer(Play.Absyn.ListPlayer listplayer) {
            while(listplayer!= null)
            {
                /* Code For ListPlayer Goes Here */
                listplayer.player_.accept(this);
                listplayer = listplayer.listplayer_;
            }
        }
        public void visitPlayer(Play.Absyn.Player player) {
            /* Code For Player Goes Here */
            System.out.println("Visiting Player");
            System.out.println(player.ident_);

            currentPlayer = new Player<Integer>(player.ident_);
            System.out.println("==============");
            if (player.listattr_ != null) {player.listattr_.accept(this);}
            players.add(currentPlayer);
        }
        public void visitListAttr(Play.Absyn.ListAttr listattr) {
            while(listattr!= null)
            {
                /* Code For ListAttr Goes Here */
                listattr.attr_.accept(this);
                listattr = listattr.listattr_;
            }
        }
        public void visitAttr(Play.Absyn.Attr attr) {} //abstract class
        public void visitAttribute(Play.Absyn.Attribute attribute) {
            /* Code For Attribute Goes Here */

            System.out.println("Visiting Attribute");
            currentPlayer.set(attribute.ident_, true);
        }
        public void visitValueAttribute(Play.Absyn.ValueAttribute valueattribute) {
            /* Code For ValueAttribute Goes Here */
            System.out.println("Visiting Value Attribute");
            currentAttribute = valueattribute.ident_;

            valueattribute.val_.accept(this);
        }
        public void visitVal(Play.Absyn.Val val) {} //abstract class
        public void visitIntegerValue(Play.Absyn.IntegerValue integervalue) {
            /* Code For IntegerValue Goes Here */

            System.out.println("Visiting Integer Value");
            currentPlayer.set(currentAttribute, integervalue.integer_);
            visitInteger(integervalue.integer_);
        }
        public void visitDoubleValue(Play.Absyn.DoubleValue doublevalue) {
            /* Code For DoubleValue Goes Here */

            System.out.println("Visiting Double Value");
            currentPlayer.set(currentAttribute, doublevalue.double_);
            visitDouble(doublevalue.double_);
        }
        public void visitStringValue(Play.Absyn.StringValue stringvalue) {
            /* Code For StringValue Goes Here */

            System.out.println("Visiting String value");
            currentPlayer.set(currentAttribute, stringvalue.string_);
            visitString(stringvalue.string_);
        }
        public void visitIdent(String i) {}
        public void visitInteger(Integer i) {}
        public void visitDouble(Double d) {}
        public void visitChar(Character c) {}
        public void visitString(String s) {}
    }
}
