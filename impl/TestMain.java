import java_cup.runtime.*;
import Swag.*;
import Swag.Absyn.*;
import java.io.*;

public class TestMain {

  public static void main(String args[]) throws Exception {
    Swag.Absyn.Prog p = BasicParser.parseTournamentFile(args[0]);
    System.out.println("Alright, managed to parse!");
    //p.accept(new generic.GenericVisitor());
  }
}
