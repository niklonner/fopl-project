package Swag.Absyn; // Java Package generated by the BNF Converter.

public class SUnionP extends Set implements Swag.Visitable
{
  public Set set_1, set_2;

  public SUnionP(Set p1, Set p2) { set_1 = p1; set_2 = p2; }

  public void accept(Swag.Visitor v) { v.visitSUnionP(this); }
}

