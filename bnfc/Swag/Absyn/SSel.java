package Swag.Absyn; // Java Package generated by the BNF Converter.

public class SSel extends Set implements Swag.Visitable
{
  public Selection selection_;

  public SSel(Selection p1) { selection_ = p1; }

  public void accept(Swag.Visitor v) { v.visitSSel(this); }
}

