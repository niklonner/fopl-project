package Play;

import Play.*;
import Play.Absyn.*;

import java_cup.runtime.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class PlayerParser {
    List<Player> players;

    private class Player {
        //TODO: Change for the real player class when refactoring the package structure
    }


    /**
     * For command-line testing
     */
    public static void main(String args[]) throws Exception {
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

            System.out.println("[Parsing output]");

            Parser parser = new Parser();
            parse_tree.accept(parser);
        }
        catch(Throwable e) {
            System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        }
    }

    public List<Player> parse(String path) {
        players = new ArrayList<>();
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
        } catch(Throwable e) {
            System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            System.err.println("     " + e.getMessage());
            System.exit(1);
        }

        return players;
    }

    private static class Parser implements Visitor {
        public void visitProg(Play.Absyn.Prog prog) {} //abstract class
        public void visitProgram(Play.Absyn.Program program) {
            /* Code For Program Goes Here */
            System.out.println("lskjdflksj");

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

            visitIdent(player.ident_);
            if (player.listattr_ != null) {player.listattr_.accept(this);}
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

            visitIdent(attribute.ident_);
        }
        public void visitValueAttribute(Play.Absyn.ValueAttribute valueattribute) {
            /* Code For ValueAttribute Goes Here */

            visitIdent(valueattribute.ident_);
            valueattribute.val_.accept(this);
        }
        public void visitVal(Play.Absyn.Val val) {} //abstract class
        public void visitIntegerValue(Play.Absyn.IntegerValue integervalue) {
            /* Code For IntegerValue Goes Here */

            visitInteger(integervalue.integer_);
        }
        public void visitDoubleValue(Play.Absyn.DoubleValue doublevalue) {
            /* Code For DoubleValue Goes Here */

            visitDouble(doublevalue.double_);
        }
        public void visitStringValue(Play.Absyn.StringValue stringvalue) {
            /* Code For StringValue Goes Here */

            visitString(stringvalue.string_);
        }
        public void visitIdent(String i) {}
        public void visitInteger(Integer i) {}
        public void visitDouble(Double d) {}
        public void visitChar(Character c) {}
        public void visitString(String s) {}
    }
}
