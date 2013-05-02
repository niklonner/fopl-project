package Swag;
import java_cup.runtime.*;
import Swag.*;
import Swag.Absyn.*;
import java.io.*;

public class BasicParser {

    public static Swag.Absyn.Prog parseTournamentFile(String fileName) {
        Yylex l = null;
        parser p;
        try {
            l = new Yylex(new FileReader(fileName));
        }
        catch(FileNotFoundException e) {
            System.err.println("Error: File not found: " + fileName);
            System.exit(1);
        }
        p = new parser(l);
        try {
            Swag.Absyn.Prog parse_tree = p.pProg();
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
            return parse_tree;
        }
        catch(Throwable e) { // sorry, but pProgram is this unspecific about its exceptions
            System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            System.err.println("     " + e.getMessage());
            System.out.println("");
            e.printStackTrace();
            System.exit(1);
        }
        return null; // to satisfy compiler
    }
}


