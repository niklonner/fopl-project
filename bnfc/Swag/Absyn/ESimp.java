package Swag.Absyn; // Java Package generated by the BNF Converter.

public class ESimp extends Exp implements Swag.Visitable
{
  public SimpleExp simpleexp_;

  public ESimp(SimpleExp p1) { simpleexp_ = p1; }

  public void accept(Swag.Visitor v) { v.visitESimp(this); }
}
