package Swag.Absyn; // Java Package generated by the BNF Converter.

public class SExp extends Stmt implements Swag.Visitable
{
  public Exp exp_;

  public SExp(Exp p1) { exp_ = p1; }

  public void accept(Swag.Visitor v) { v.visitSExp(this); }
}

