package Swag;

public interface Visitor
{
  public void visitProgram(Swag.Absyn.Program p);
  public void visitProg(Swag.Absyn.Prog p);
  public void visitTournament(Swag.Absyn.Tournament p);
  public void visitListTournament(Swag.Absyn.ListTournament p);
  public void visitListStmt(Swag.Absyn.ListStmt p);
  public void visitStmt(Swag.Absyn.Stmt p);
  public void visitSExp(Swag.Absyn.SExp p);
  public void visitSAss(Swag.Absyn.SAss p);
  public void visitSSend(Swag.Absyn.SSend p);
  public void visitSimpleExp(Swag.Absyn.SimpleExp p);
  public void visitSimpExpStr(Swag.Absyn.SimpExpStr p);
  public void visitSimpExpInt(Swag.Absyn.SimpExpInt p);
  public void visitExp(Swag.Absyn.Exp p);
  public void visitESimp(Swag.Absyn.ESimp p);
  public void visitESet(Swag.Absyn.ESet p);
  public void visitSet(Swag.Absyn.Set p);
  public void visitSDiff(Swag.Absyn.SDiff p);
  public void visitSUnionP(Swag.Absyn.SUnionP p);
  public void visitSIntersect(Swag.Absyn.SIntersect p);
  public void visitSNot(Swag.Absyn.SNot p);
  public void visitSHasAttr(Swag.Absyn.SHasAttr p);
  public void visitSVar(Swag.Absyn.SVar p);
  public void visitSSel(Swag.Absyn.SSel p);
  public void visitSUnion(Swag.Absyn.SUnion p);
  public void visitStringComparison(Swag.Absyn.StringComparison p);
  public void visitSCmp(Swag.Absyn.SCmp p);
  public void visitComparisonOp(Swag.Absyn.ComparisonOp p);
  public void visitCmpOpEq(Swag.Absyn.CmpOpEq p);
  public void visitCmpOpLt(Swag.Absyn.CmpOpLt p);
  public void visitCmpOpGt(Swag.Absyn.CmpOpGt p);
  public void visitCmpOpLeq(Swag.Absyn.CmpOpLeq p);
  public void visitCmpOpGeq(Swag.Absyn.CmpOpGeq p);
  public void visitPlayer(Swag.Absyn.Player p);
  public void visitListSet(Swag.Absyn.ListSet p);
  public void visitSelection(Swag.Absyn.Selection p);
  public void visitSelTake(Swag.Absyn.SelTake p);
  public void visitLocation(Swag.Absyn.Location p);
  public void visitLTop(Swag.Absyn.LTop p);
  public void visitLBottom(Swag.Absyn.LBottom p);
  public void visitPart(Swag.Absyn.Part p);
  public void visitPInt(Swag.Absyn.PInt p);
  public void visitPPerc(Swag.Absyn.PPerc p);
  public void visitPFrac(Swag.Absyn.PFrac p);


  public void visitIdent(String i);
  public void visitInteger(Integer i);
  public void visitDouble(Double d);
  public void visitChar(Character c);
  public void visitString(String s);
}

