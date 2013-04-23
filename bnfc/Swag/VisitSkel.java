package Swag;
import Swag.Absyn.*;
/*** BNFC-Generated Visitor Design Pattern Skeleton. ***/
/* This implements the common visitor design pattern.
   Tests show it to be slightly less efficient than the
   instanceof method, but easier to use.
   Note that this method uses Visitor-traversal of lists, so
   List.accept() does NOT traverse the list. This allows different
   algorithms to use context information differently. */

public class VisitSkel implements Visitor
{
  public void visitProgram(Swag.Absyn.Program program) {} //abstract class
  public void visitProg(Swag.Absyn.Prog prog)
  {
    /* Code For Prog Goes Here */

    if (prog.listtournament_ != null) {prog.listtournament_.accept(this);}
  }
  public void visitTournament(Swag.Absyn.Tournament tournament)
  {
    /* Code For Tournament Goes Here */

    visitIdent(tournament.ident_);
    if (tournament.liststmt_ != null) {tournament.liststmt_.accept(this);}
  }
  public void visitListTournament(Swag.Absyn.ListTournament listtournament)
  {
    while(listtournament!= null)
    {
      /* Code For ListTournament Goes Here */
    listtournament.tournament_.accept(this);
      listtournament = listtournament.listtournament_;
    }
  }
  public void visitListStmt(Swag.Absyn.ListStmt liststmt)
  {
    while(liststmt!= null)
    {
      /* Code For ListStmt Goes Here */
    liststmt.stmt_.accept(this);
      liststmt = liststmt.liststmt_;
    }
  }
  public void visitStmt(Swag.Absyn.Stmt stmt) {} //abstract class
  public void visitSExp(Swag.Absyn.SExp sexp)
  {
    /* Code For SExp Goes Here */

    sexp.exp_.accept(this);
  }
  public void visitSAss(Swag.Absyn.SAss sass)
  {
    /* Code For SAss Goes Here */

    visitIdent(sass.ident_);
    sass.exp_.accept(this);
  }
  public void visitSSend(Swag.Absyn.SSend ssend)
  {
    /* Code For SSend Goes Here */

    ssend.set_.accept(this);
    visitIdent(ssend.ident_);
  }
  public void visitSimpleExp(Swag.Absyn.SimpleExp simpleexp) {} //abstract class
  public void visitSimpExpStr(Swag.Absyn.SimpExpStr simpexpstr)
  {
    /* Code For SimpExpStr Goes Here */

    visitString(simpexpstr.string_);
  }
  public void visitSimpExpInt(Swag.Absyn.SimpExpInt simpexpint)
  {
    /* Code For SimpExpInt Goes Here */

    visitInteger(simpexpint.integer_);
  }
  public void visitExp(Swag.Absyn.Exp exp) {} //abstract class
  public void visitESimp(Swag.Absyn.ESimp esimp)
  {
    /* Code For ESimp Goes Here */

    esimp.simpleexp_.accept(this);
  }
  public void visitESet(Swag.Absyn.ESet eset)
  {
    /* Code For ESet Goes Here */

    eset.set_.accept(this);
  }
  public void visitSet(Swag.Absyn.Set set) {} //abstract class
  public void visitSDiff(Swag.Absyn.SDiff sdiff)
  {
    /* Code For SDiff Goes Here */

    sdiff.set_1.accept(this);
    sdiff.set_2.accept(this);
  }
  public void visitSUnionP(Swag.Absyn.SUnionP sunionp)
  {
    /* Code For SUnionP Goes Here */

    sunionp.set_1.accept(this);
    sunionp.set_2.accept(this);
  }
  public void visitSIntersect(Swag.Absyn.SIntersect sintersect)
  {
    /* Code For SIntersect Goes Here */

    sintersect.set_1.accept(this);
    sintersect.set_2.accept(this);
  }
  public void visitSNot(Swag.Absyn.SNot snot)
  {
    /* Code For SNot Goes Here */

    snot.set_.accept(this);
  }
  public void visitSHasAttr(Swag.Absyn.SHasAttr shasattr)
  {
    /* Code For SHasAttr Goes Here */

    visitString(shasattr.string_);
  }
  public void visitSVar(Swag.Absyn.SVar svar)
  {
    /* Code For SVar Goes Here */

    visitIdent(svar.ident_);
  }
  public void visitSSel(Swag.Absyn.SSel ssel)
  {
    /* Code For SSel Goes Here */

    ssel.selection_.accept(this);
  }
  public void visitSUnion(Swag.Absyn.SUnion sunion)
  {
    /* Code For SUnion Goes Here */

    if (sunion.listset_ != null) {sunion.listset_.accept(this);}
  }
  public void visitStringComparison(Swag.Absyn.StringComparison stringcomparison) {} //abstract class
  public void visitSCmp(Swag.Absyn.SCmp scmp)
  {
    /* Code For SCmp Goes Here */

    visitString(scmp.string_);
    scmp.comparisonop_.accept(this);
    scmp.simpleexp_.accept(this);
  }
  public void visitComparisonOp(Swag.Absyn.ComparisonOp comparisonop) {} //abstract class
  public void visitCmpOpEq(Swag.Absyn.CmpOpEq cmpopeq)
  {
    /* Code For CmpOpEq Goes Here */

  }
  public void visitCmpOpLt(Swag.Absyn.CmpOpLt cmpoplt)
  {
    /* Code For CmpOpLt Goes Here */

  }
  public void visitCmpOpGt(Swag.Absyn.CmpOpGt cmpopgt)
  {
    /* Code For CmpOpGt Goes Here */

  }
  public void visitCmpOpLeq(Swag.Absyn.CmpOpLeq cmpopleq)
  {
    /* Code For CmpOpLeq Goes Here */

  }
  public void visitCmpOpGeq(Swag.Absyn.CmpOpGeq cmpopgeq)
  {
    /* Code For CmpOpGeq Goes Here */

  }
  public void visitPlayer(Swag.Absyn.Player player)
  {
    /* Code For Player Goes Here */

    visitIdent(player.ident_);
  }
  public void visitListSet(Swag.Absyn.ListSet listset)
  {
    while(listset!= null)
    {
      /* Code For ListSet Goes Here */
    listset.set_.accept(this);
      listset = listset.listset_;
    }
  }
  public void visitSelection(Swag.Absyn.Selection selection) {} //abstract class
  public void visitSelTake(Swag.Absyn.SelTake seltake)
  {
    /* Code For SelTake Goes Here */

    seltake.location_.accept(this);
    seltake.part_.accept(this);
    seltake.set_.accept(this);
  }
  public void visitLocation(Swag.Absyn.Location location) {} //abstract class
  public void visitLTop(Swag.Absyn.LTop ltop)
  {
    /* Code For LTop Goes Here */

  }
  public void visitLBottom(Swag.Absyn.LBottom lbottom)
  {
    /* Code For LBottom Goes Here */

  }
  public void visitPart(Swag.Absyn.Part part) {} //abstract class
  public void visitPInt(Swag.Absyn.PInt pint)
  {
    /* Code For PInt Goes Here */

    visitInteger(pint.integer_);
  }
  public void visitPPerc(Swag.Absyn.PPerc pperc)
  {
    /* Code For PPerc Goes Here */

    visitInteger(pperc.integer_);
  }
  public void visitPFrac(Swag.Absyn.PFrac pfrac)
  {
    /* Code For PFrac Goes Here */

    visitInteger(pfrac.integer_1);
    visitInteger(pfrac.integer_2);
  }
  public void visitIdent(String i) {}
  public void visitInteger(Integer i) {}
  public void visitDouble(Double d) {}
  public void visitChar(Character c) {}
  public void visitString(String s) {}
}
