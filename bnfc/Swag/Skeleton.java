package Swag;
import Swag.Absyn.*;
/*** BNFC-Generated Skeleton function. ***/
/* You will probably want to save this in a new file.
   Then do two search-and-replaces.
   First replace "skel" with a real funciton name.
   Then replace Object with a real return type. */

public class Skeleton
{

  public static Object skel(Swag.Absyn.Program foo)
  {
    if (foo instanceof Swag.Absyn.Prog)
    {
       Swag.Absyn.Prog prog = (Swag.Absyn.Prog) foo;

       /* Code For Prog Goes Here */

       skel(prog.listtournament_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Tournament foo)
  {
    if (foo instanceof Swag.Absyn.Tournament)
    {
       Swag.Absyn.Tournament tournament = (Swag.Absyn.Tournament) foo;

       /* Code For Tournament Goes Here */

       skel(tournament.ident_);

       skel(tournament.liststmt_);


       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.ListTournament foo)
  {
    if (foo == null)
    {
       /* Optional End of List Code Goes Here */
       return null;
    }
    else
    {
      /* Optional List Member Code Goes Here */
      skel(foo.tournament_);
      skel(foo.listtournament_);
      return null;
    }

  }

  public static Object skel(Swag.Absyn.ListStmt foo)
  {
    if (foo == null)
    {
       /* Optional End of List Code Goes Here */
       return null;
    }
    else
    {
      /* Optional List Member Code Goes Here */
      skel(foo.stmt_);
      skel(foo.liststmt_);
      return null;
    }

  }

  public static Object skel(Swag.Absyn.Stmt foo)
  {
    if (foo instanceof Swag.Absyn.SExp)
    {
       Swag.Absyn.SExp sexp = (Swag.Absyn.SExp) foo;

       /* Code For SExp Goes Here */

       skel(sexp.exp_);

       return null;
    }

    if (foo instanceof Swag.Absyn.SAss)
    {
       Swag.Absyn.SAss sass = (Swag.Absyn.SAss) foo;

       /* Code For SAss Goes Here */

       skel(sass.ident_);

       skel(sass.exp_);

       return null;
    }

    if (foo instanceof Swag.Absyn.SSend)
    {
       Swag.Absyn.SSend ssend = (Swag.Absyn.SSend) foo;

       /* Code For SSend Goes Here */


       skel(ssend.set_);

       skel(ssend.ident_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.SimpleExp foo)
  {
    if (foo instanceof Swag.Absyn.SimpExpStr)
    {
       Swag.Absyn.SimpExpStr simpexpstr = (Swag.Absyn.SimpExpStr) foo;

       /* Code For SimpExpStr Goes Here */

       skel(simpexpstr.string_);

       return null;
    }

    if (foo instanceof Swag.Absyn.SimpExpInt)
    {
       Swag.Absyn.SimpExpInt simpexpint = (Swag.Absyn.SimpExpInt) foo;

       /* Code For SimpExpInt Goes Here */

       skel(simpexpint.integer_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Exp foo)
  {
    if (foo instanceof Swag.Absyn.ESimp)
    {
       Swag.Absyn.ESimp esimp = (Swag.Absyn.ESimp) foo;

       /* Code For ESimp Goes Here */

       skel(esimp.simpleexp_);

       return null;
    }

    if (foo instanceof Swag.Absyn.ESet)
    {
       Swag.Absyn.ESet eset = (Swag.Absyn.ESet) foo;

       /* Code For ESet Goes Here */

       skel(eset.set_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Set foo)
  {
    if (foo instanceof Swag.Absyn.SDiff)
    {
       Swag.Absyn.SDiff sdiff = (Swag.Absyn.SDiff) foo;

       /* Code For SDiff Goes Here */

       skel(sdiff.set_1);

       skel(sdiff.set_2);

       return null;
    }

    if (foo instanceof Swag.Absyn.SUnionP)
    {
       Swag.Absyn.SUnionP sunionp = (Swag.Absyn.SUnionP) foo;

       /* Code For SUnionP Goes Here */

       skel(sunionp.set_1);

       skel(sunionp.set_2);

       return null;
    }


    if (foo instanceof Swag.Absyn.SIntersect)
    {
       Swag.Absyn.SIntersect sintersect = (Swag.Absyn.SIntersect) foo;

       /* Code For SIntersect Goes Here */

       skel(sintersect.set_1);

       skel(sintersect.set_2);

       return null;
    }


    if (foo instanceof Swag.Absyn.SNot)
    {
       Swag.Absyn.SNot snot = (Swag.Absyn.SNot) foo;

       /* Code For SNot Goes Here */



       skel(snot.set_);

       return null;
    }


    if (foo instanceof Swag.Absyn.SHasAttr)
    {
       Swag.Absyn.SHasAttr shasattr = (Swag.Absyn.SHasAttr) foo;

       /* Code For SHasAttr Goes Here */


       skel(shasattr.string_);

       return null;
    }


    if (foo instanceof Swag.Absyn.SVar)
    {
       Swag.Absyn.SVar svar = (Swag.Absyn.SVar) foo;

       /* Code For SVar Goes Here */

       skel(svar.ident_);

       return null;
    }

    if (foo instanceof Swag.Absyn.SSel)
    {
       Swag.Absyn.SSel ssel = (Swag.Absyn.SSel) foo;

       /* Code For SSel Goes Here */

       skel(ssel.selection_);

       return null;
    }

    if (foo instanceof Swag.Absyn.SUnion)
    {
       Swag.Absyn.SUnion sunion = (Swag.Absyn.SUnion) foo;

       /* Code For SUnion Goes Here */


       skel(sunion.listset_);


       return null;
    }



    return null;
  }

  public static Object skel(Swag.Absyn.StringComparison foo)
  {
    if (foo instanceof Swag.Absyn.SCmp)
    {
       Swag.Absyn.SCmp scmp = (Swag.Absyn.SCmp) foo;

       /* Code For SCmp Goes Here */

       skel(scmp.string_);
       skel(scmp.comparisonop_);
       skel(scmp.simpleexp_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.ComparisonOp foo)
  {
    if (foo instanceof Swag.Absyn.CmpOpEq)
    {
       Swag.Absyn.CmpOpEq cmpopeq = (Swag.Absyn.CmpOpEq) foo;

       /* Code For CmpOpEq Goes Here */


       return null;
    }

    if (foo instanceof Swag.Absyn.CmpOpLt)
    {
       Swag.Absyn.CmpOpLt cmpoplt = (Swag.Absyn.CmpOpLt) foo;

       /* Code For CmpOpLt Goes Here */


       return null;
    }

    if (foo instanceof Swag.Absyn.CmpOpGt)
    {
       Swag.Absyn.CmpOpGt cmpopgt = (Swag.Absyn.CmpOpGt) foo;

       /* Code For CmpOpGt Goes Here */


       return null;
    }

    if (foo instanceof Swag.Absyn.CmpOpLeq)
    {
       Swag.Absyn.CmpOpLeq cmpopleq = (Swag.Absyn.CmpOpLeq) foo;

       /* Code For CmpOpLeq Goes Here */


       return null;
    }

    if (foo instanceof Swag.Absyn.CmpOpGeq)
    {
       Swag.Absyn.CmpOpGeq cmpopgeq = (Swag.Absyn.CmpOpGeq) foo;

       /* Code For CmpOpGeq Goes Here */


       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Player foo)
  {
    if (foo instanceof Swag.Absyn.Player)
    {
       Swag.Absyn.Player player = (Swag.Absyn.Player) foo;

       /* Code For Player Goes Here */

       skel(player.ident_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.ListSet foo)
  {
    if (foo == null)
    {
       /* Optional End of List Code Goes Here */
       return null;
    }
    else
    {
      /* Optional List Member Code Goes Here */
      skel(foo.set_);
      skel(foo.listset_);
      return null;
    }

  }

  public static Object skel(Swag.Absyn.Selection foo)
  {
    if (foo instanceof Swag.Absyn.SelTake)
    {
       Swag.Absyn.SelTake seltake = (Swag.Absyn.SelTake) foo;

       /* Code For SelTake Goes Here */


       skel(seltake.location_);
       skel(seltake.part_);

       skel(seltake.set_);

       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Location foo)
  {
    if (foo instanceof Swag.Absyn.LTop)
    {
       Swag.Absyn.LTop ltop = (Swag.Absyn.LTop) foo;

       /* Code For LTop Goes Here */


       return null;
    }

    if (foo instanceof Swag.Absyn.LBottom)
    {
       Swag.Absyn.LBottom lbottom = (Swag.Absyn.LBottom) foo;

       /* Code For LBottom Goes Here */


       return null;
    }


    return null;
  }

  public static Object skel(Swag.Absyn.Part foo)
  {
    if (foo instanceof Swag.Absyn.PInt)
    {
       Swag.Absyn.PInt pint = (Swag.Absyn.PInt) foo;

       /* Code For PInt Goes Here */

       skel(pint.integer_);

       return null;
    }

    if (foo instanceof Swag.Absyn.PPerc)
    {
       Swag.Absyn.PPerc pperc = (Swag.Absyn.PPerc) foo;

       /* Code For PPerc Goes Here */

       skel(pperc.integer_);


       return null;
    }

    if (foo instanceof Swag.Absyn.PFrac)
    {
       Swag.Absyn.PFrac pfrac = (Swag.Absyn.PFrac) foo;

       /* Code For PFrac Goes Here */

       skel(pfrac.integer_1);

       skel(pfrac.integer_2);

       return null;
    }


    return null;
  }


  public static Object skel(Integer i) { return null; }
  public static Object skel(Double d) { return null; }
  public static Object skel(String s) { return null; }
}

