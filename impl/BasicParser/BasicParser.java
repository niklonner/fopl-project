package Swag;
import java_cup.runtime.*;
import Swag.*;
import Swag.Absyn.*;
import java.io.*;

public class BasicParser {

    public static Swag.Absyn.Prog parseTournamentFile(String fileName) {
        Yylex l = null;
        parser p;
        try
            {
                l = new Yylex(new FileReader(fileName));
            }
        catch(FileNotFoundException e)
            {
                System.err.println("Error: File not found: " + fileName);
                System.exit(1);
            }
        p = new parser(l);
        try
            {
                return p.pProg();
            }
        catch(Exception e) // sorry, but pProgram is this unspecific about its exceptions
            {
                e.printStackTrace();
                System.exit(1);
            }
	return null; // to satisfy compiler
    }
}


