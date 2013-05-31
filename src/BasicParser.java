package <<package>>;
import java_cup.runtime.*;
import <<package>>.*;
import <<package>>.Absyn.*;
import java.io.*;

import util.ContextException;

/**
 * Creates a parse tree using the bnfc-generated files.
 * During compilation of the project, this file is copied to the Play and
 * Swag folder in the bnfc directory.
 */
public class BasicParser {

    public static <<package>>.Absyn.Prog parseTournamentFile(String path) throws ContextException, FileNotFoundException {
        return parse(new FileReader(path));
    }

    public static <<package>>.Absyn.Prog parseTournamentString(String sourceCode) throws ContextException {
        return parse(new StringReader(sourceCode));
    }


    private static <<package>>.Absyn.Prog parse(Reader reader) throws ContextException {
        Yylex l = null;
        parser p;
        l = new Yylex(reader);
        p = new parser(l);
        <<package>>.Absyn.Prog parse_tree = null;
        try {
            parse_tree = p.pProg();
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
        } catch(Throwable e) { // sorry, but pProgram is this unspecific about its exceptions
            ContextException exc = new ContextException(e);
            exc.setLine(l.line_num());
            exc.setContext(l.buff());
            throw exc;
        }
        return parse_tree;
    }
}


