package Swag.Absyn; // Java Package generated by the BNF Converter.

public class ListStmt implements Swag.Visitable
{
  public Stmt stmt_;
  public ListStmt liststmt_;

  public ListStmt(Stmt p1, ListStmt p2) { stmt_ = p1; liststmt_ = p2; }
  public ListStmt reverse()
  {
    if (liststmt_ == null) return this;
    else
    {
      ListStmt tmp = liststmt_.reverse(this);
      liststmt_ = null;
      return tmp;
    }
  }
  public ListStmt reverse(ListStmt prev)
  {
    if (liststmt_ == null)
    {
      liststmt_ = prev;
      return this;
    }
    else
    {
      ListStmt tmp = liststmt_.reverse(this);
      liststmt_ = prev;
      return tmp;
    }
  }


  public void accept(Swag.Visitor v) { v.visitListStmt(this); }
}
