package Swag.Absyn; // Java Package generated by the BNF Converter.

public class Tournament  implements Swag.Visitable
{
  public String ident_;
  public ListStmt liststmt_;

  public Tournament(String p1, ListStmt p2) { ident_ = p1; liststmt_ = p2; }

  public void accept(Swag.Visitor v) { v.visitTournament(this); }
}
