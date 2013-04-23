package Swag;
import Swag.Absyn.*;

public class PrettyPrinter
{
  //For certain applications increasing the initial size of the buffer may improve performance.
  private static final int INITIAL_BUFFER_SIZE = 128;
  //You may wish to change the parentheses used in precedence.
  private static final String _L_PAREN = new String("(");
  private static final String _R_PAREN = new String(")");
  //You may wish to change render
  private static void render(String s)
  {
    if (s.equals("{"))
    {
       buf_.append("\n");
       indent();
       buf_.append(s);
       _n_ = _n_ + 2;
       buf_.append("\n");
       indent();
    }
    else if (s.equals("(") || s.equals("["))
       buf_.append(s);
    else if (s.equals(")") || s.equals("]"))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals("}"))
    {
       _n_ = _n_ - 2;
       backup();
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals(","))
    {
       backup();
       buf_.append(s);
       buf_.append(" ");
    }
    else if (s.equals(";"))
    {
       backup();
       buf_.append(s);
       buf_.append("\n");
       indent();
    }
    else if (s.equals("")) return;
    else
    {
       buf_.append(s);
       buf_.append(" ");
    }
  }


  //  print and show methods are defined for each Entry Point type.
  public static String print(Swag.Absyn.Program foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Program foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Tournament foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Tournament foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.ListTournament foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.ListTournament foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.ListStmt foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.ListStmt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Stmt foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Stmt foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.SimpleExp foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.SimpleExp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Exp foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Exp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Set foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Set foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.StringComparison foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.StringComparison foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.ComparisonOp foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.ComparisonOp foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Player foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Player foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.ListSet foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.ListSet foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Selection foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Selection foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Location foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Location foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String print(Swag.Absyn.Part foo)
  {
    pp(foo, 0);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  public static String show(Swag.Absyn.Part foo)
  {
    sh(foo);
    String temp = buf_.toString();
    buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
    return temp;
  }
  /***   You shouldn't need to change anything beyond this point.   ***/

  private static void pp(Swag.Absyn.Program foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.Prog)
    {
       Swag.Absyn.Prog _prog = (Swag.Absyn.Prog) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_prog.listtournament_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Tournament foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.Tournament)
    {
       Swag.Absyn.Tournament _tournament = (Swag.Absyn.Tournament) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_tournament.ident_, 0);
       render("{");
       pp(_tournament.liststmt_, 0);
       render("}");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.ListTournament foo, int _i_)
  {
    while (foo != null)
    {
      if (foo.listtournament_ == null)
      {
        pp(foo.tournament_, 0);
        render(";");
      }
      else
      {
        pp(foo.tournament_, 0);
        render(";");
      }
      foo = foo.listtournament_;
    }
  }

  private static void pp(Swag.Absyn.ListStmt foo, int _i_)
  {
    while (foo != null)
    {
      if (foo.liststmt_ == null)
      {
        pp(foo.stmt_, 0);
        render(";");
      }
      else
      {
        pp(foo.stmt_, 0);
        render(";");
      }
      foo = foo.liststmt_;
    }
  }

  private static void pp(Swag.Absyn.Stmt foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.SExp)
    {
       Swag.Absyn.SExp _sexp = (Swag.Absyn.SExp) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_sexp.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SAss)
    {
       Swag.Absyn.SAss _sass = (Swag.Absyn.SAss) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_sass.ident_, 0);
       render("=");
       pp(_sass.exp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SSend)
    {
       Swag.Absyn.SSend _ssend = (Swag.Absyn.SSend) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("send");
       pp(_ssend.set_, 0);
       render("to");
       pp(_ssend.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.SimpleExp foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.SimpExpStr)
    {
       Swag.Absyn.SimpExpStr _simpexpstr = (Swag.Absyn.SimpExpStr) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_simpexpstr.string_);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SimpExpInt)
    {
       Swag.Absyn.SimpExpInt _simpexpint = (Swag.Absyn.SimpExpInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_simpexpint.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Exp foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.ESimp)
    {
       Swag.Absyn.ESimp _esimp = (Swag.Absyn.ESimp) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_esimp.simpleexp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.ESet)
    {
       Swag.Absyn.ESet _eset = (Swag.Absyn.ESet) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_eset.set_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Set foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.SDiff)
    {
       Swag.Absyn.SDiff _sdiff = (Swag.Absyn.SDiff) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_sdiff.set_1, 0);
       render("minus");
       pp(_sdiff.set_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SUnionP)
    {
       Swag.Absyn.SUnionP _sunionp = (Swag.Absyn.SUnionP) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_sunionp.set_1, 0);
       render("+");
       pp(_sunionp.set_2, 1);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SIntersect)
    {
       Swag.Absyn.SIntersect _sintersect = (Swag.Absyn.SIntersect) foo;
       if (_i_ > 1) render(_L_PAREN);
       pp(_sintersect.set_1, 1);
       render("intersect");
       pp(_sintersect.set_2, 2);
       if (_i_ > 1) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SNot)
    {
       Swag.Absyn.SNot _snot = (Swag.Absyn.SNot) foo;
       if (_i_ > 2) render(_L_PAREN);
       render("not");
       render("in");
       pp(_snot.set_, 0);
       if (_i_ > 2) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SHasAttr)
    {
       Swag.Absyn.SHasAttr _shasattr = (Swag.Absyn.SHasAttr) foo;
       if (_i_ > 3) render(_L_PAREN);
       render("with");
       printQuoted(_shasattr.string_);
       if (_i_ > 3) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SVar)
    {
       Swag.Absyn.SVar _svar = (Swag.Absyn.SVar) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_svar.ident_, 0);
       if (_i_ > 4) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SSel)
    {
       Swag.Absyn.SSel _ssel = (Swag.Absyn.SSel) foo;
       if (_i_ > 4) render(_L_PAREN);
       pp(_ssel.selection_, 0);
       if (_i_ > 4) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.SUnion)
    {
       Swag.Absyn.SUnion _sunion = (Swag.Absyn.SUnion) foo;
       if (_i_ > 4) render(_L_PAREN);
       render("[");
       pp(_sunion.listset_, 0);
       render("]");
       if (_i_ > 4) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.StringComparison foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.SCmp)
    {
       Swag.Absyn.SCmp _scmp = (Swag.Absyn.SCmp) foo;
       if (_i_ > 0) render(_L_PAREN);
       printQuoted(_scmp.string_);
       pp(_scmp.comparisonop_, 0);
       pp(_scmp.simpleexp_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.ComparisonOp foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.CmpOpEq)
    {
       Swag.Absyn.CmpOpEq _cmpopeq = (Swag.Absyn.CmpOpEq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("=");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.CmpOpLt)
    {
       Swag.Absyn.CmpOpLt _cmpoplt = (Swag.Absyn.CmpOpLt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("<");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.CmpOpGt)
    {
       Swag.Absyn.CmpOpGt _cmpopgt = (Swag.Absyn.CmpOpGt) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(">");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.CmpOpLeq)
    {
       Swag.Absyn.CmpOpLeq _cmpopleq = (Swag.Absyn.CmpOpLeq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("<=");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.CmpOpGeq)
    {
       Swag.Absyn.CmpOpGeq _cmpopgeq = (Swag.Absyn.CmpOpGeq) foo;
       if (_i_ > 0) render(_L_PAREN);
       render(">=");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Player foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.Player)
    {
       Swag.Absyn.Player _player = (Swag.Absyn.Player) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_player.ident_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.ListSet foo, int _i_)
  {
    while (foo != null)
    {
      if (foo.listset_ == null)
      {
        pp(foo.set_, 0);

      }
      else
      {
        pp(foo.set_, 0);
        render(",");
      }
      foo = foo.listset_;
    }
  }

  private static void pp(Swag.Absyn.Selection foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.SelTake)
    {
       Swag.Absyn.SelTake _seltake = (Swag.Absyn.SelTake) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("take");
       pp(_seltake.location_, 0);
       pp(_seltake.part_, 0);
       render("from");
       pp(_seltake.set_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Location foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.LTop)
    {
       Swag.Absyn.LTop _ltop = (Swag.Absyn.LTop) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("top");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.LBottom)
    {
       Swag.Absyn.LBottom _lbottom = (Swag.Absyn.LBottom) foo;
       if (_i_ > 0) render(_L_PAREN);
       render("bottom");
       if (_i_ > 0) render(_R_PAREN);
    }
  }

  private static void pp(Swag.Absyn.Part foo, int _i_)
  {
    if (foo instanceof Swag.Absyn.PInt)
    {
       Swag.Absyn.PInt _pint = (Swag.Absyn.PInt) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pint.integer_, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.PPerc)
    {
       Swag.Absyn.PPerc _pperc = (Swag.Absyn.PPerc) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pperc.integer_, 0);
       render("%");
       if (_i_ > 0) render(_R_PAREN);
    }
    if (foo instanceof Swag.Absyn.PFrac)
    {
       Swag.Absyn.PFrac _pfrac = (Swag.Absyn.PFrac) foo;
       if (_i_ > 0) render(_L_PAREN);
       pp(_pfrac.integer_1, 0);
       render("/");
       pp(_pfrac.integer_2, 0);
       if (_i_ > 0) render(_R_PAREN);
    }
  }


  private static void sh(Swag.Absyn.Program foo)
  {
    if (foo instanceof Swag.Absyn.Prog)
    {
       Swag.Absyn.Prog _prog = (Swag.Absyn.Prog) foo;
       render("(");
       render("Prog");
       render("[");
       sh(_prog.listtournament_);
       render("]");
       render(")");
    }
  }

  private static void sh(Swag.Absyn.Tournament foo)
  {
    if (foo instanceof Swag.Absyn.Tournament)
    {
       Swag.Absyn.Tournament _tournament = (Swag.Absyn.Tournament) foo;
       render("(");
       render("Tournament");
       sh(_tournament.ident_);
       render("[");
       sh(_tournament.liststmt_);
       render("]");
       render(")");
    }
  }

  private static void sh(Swag.Absyn.ListTournament foo)
  {
    while (foo != null)
    {
      if (foo.listtournament_ == null)
      {
        sh(foo.tournament_);
      }
      else
      {
        sh(foo.tournament_);
        render(",");
      }
      foo = foo.listtournament_;
    }
  }

  private static void sh(Swag.Absyn.ListStmt foo)
  {
    while (foo != null)
    {
      if (foo.liststmt_ == null)
      {
        sh(foo.stmt_);
      }
      else
      {
        sh(foo.stmt_);
        render(",");
      }
      foo = foo.liststmt_;
    }
  }

  private static void sh(Swag.Absyn.Stmt foo)
  {
    if (foo instanceof Swag.Absyn.SExp)
    {
       Swag.Absyn.SExp _sexp = (Swag.Absyn.SExp) foo;
       render("(");
       render("SExp");
       sh(_sexp.exp_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SAss)
    {
       Swag.Absyn.SAss _sass = (Swag.Absyn.SAss) foo;
       render("(");
       render("SAss");
       sh(_sass.ident_);
       sh(_sass.exp_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SSend)
    {
       Swag.Absyn.SSend _ssend = (Swag.Absyn.SSend) foo;
       render("(");
       render("SSend");
       sh(_ssend.set_);
       sh(_ssend.ident_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.SimpleExp foo)
  {
    if (foo instanceof Swag.Absyn.SimpExpStr)
    {
       Swag.Absyn.SimpExpStr _simpexpstr = (Swag.Absyn.SimpExpStr) foo;
       render("(");
       render("SimpExpStr");
       sh(_simpexpstr.string_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SimpExpInt)
    {
       Swag.Absyn.SimpExpInt _simpexpint = (Swag.Absyn.SimpExpInt) foo;
       render("(");
       render("SimpExpInt");
       sh(_simpexpint.integer_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.Exp foo)
  {
    if (foo instanceof Swag.Absyn.ESimp)
    {
       Swag.Absyn.ESimp _esimp = (Swag.Absyn.ESimp) foo;
       render("(");
       render("ESimp");
       sh(_esimp.simpleexp_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.ESet)
    {
       Swag.Absyn.ESet _eset = (Swag.Absyn.ESet) foo;
       render("(");
       render("ESet");
       sh(_eset.set_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.Set foo)
  {
    if (foo instanceof Swag.Absyn.SDiff)
    {
       Swag.Absyn.SDiff _sdiff = (Swag.Absyn.SDiff) foo;
       render("(");
       render("SDiff");
       sh(_sdiff.set_1);
       sh(_sdiff.set_2);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SUnionP)
    {
       Swag.Absyn.SUnionP _sunionp = (Swag.Absyn.SUnionP) foo;
       render("(");
       render("SUnionP");
       sh(_sunionp.set_1);
       sh(_sunionp.set_2);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SIntersect)
    {
       Swag.Absyn.SIntersect _sintersect = (Swag.Absyn.SIntersect) foo;
       render("(");
       render("SIntersect");
       sh(_sintersect.set_1);
       sh(_sintersect.set_2);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SNot)
    {
       Swag.Absyn.SNot _snot = (Swag.Absyn.SNot) foo;
       render("(");
       render("SNot");
       sh(_snot.set_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SHasAttr)
    {
       Swag.Absyn.SHasAttr _shasattr = (Swag.Absyn.SHasAttr) foo;
       render("(");
       render("SHasAttr");
       sh(_shasattr.string_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SVar)
    {
       Swag.Absyn.SVar _svar = (Swag.Absyn.SVar) foo;
       render("(");
       render("SVar");
       sh(_svar.ident_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SSel)
    {
       Swag.Absyn.SSel _ssel = (Swag.Absyn.SSel) foo;
       render("(");
       render("SSel");
       sh(_ssel.selection_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.SUnion)
    {
       Swag.Absyn.SUnion _sunion = (Swag.Absyn.SUnion) foo;
       render("(");
       render("SUnion");
       render("[");
       sh(_sunion.listset_);
       render("]");
       render(")");
    }
  }

  private static void sh(Swag.Absyn.StringComparison foo)
  {
    if (foo instanceof Swag.Absyn.SCmp)
    {
       Swag.Absyn.SCmp _scmp = (Swag.Absyn.SCmp) foo;
       render("(");
       render("SCmp");
       sh(_scmp.string_);
       sh(_scmp.comparisonop_);
       sh(_scmp.simpleexp_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.ComparisonOp foo)
  {
    if (foo instanceof Swag.Absyn.CmpOpEq)
    {
       Swag.Absyn.CmpOpEq _cmpopeq = (Swag.Absyn.CmpOpEq) foo;
       render("CmpOpEq");
    }
    if (foo instanceof Swag.Absyn.CmpOpLt)
    {
       Swag.Absyn.CmpOpLt _cmpoplt = (Swag.Absyn.CmpOpLt) foo;
       render("CmpOpLt");
    }
    if (foo instanceof Swag.Absyn.CmpOpGt)
    {
       Swag.Absyn.CmpOpGt _cmpopgt = (Swag.Absyn.CmpOpGt) foo;
       render("CmpOpGt");
    }
    if (foo instanceof Swag.Absyn.CmpOpLeq)
    {
       Swag.Absyn.CmpOpLeq _cmpopleq = (Swag.Absyn.CmpOpLeq) foo;
       render("CmpOpLeq");
    }
    if (foo instanceof Swag.Absyn.CmpOpGeq)
    {
       Swag.Absyn.CmpOpGeq _cmpopgeq = (Swag.Absyn.CmpOpGeq) foo;
       render("CmpOpGeq");
    }
  }

  private static void sh(Swag.Absyn.Player foo)
  {
    if (foo instanceof Swag.Absyn.Player)
    {
       Swag.Absyn.Player _player = (Swag.Absyn.Player) foo;
       render("(");
       render("Player");
       sh(_player.ident_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.ListSet foo)
  {
    while (foo != null)
    {
      if (foo.listset_ == null)
      {
        sh(foo.set_);
      }
      else
      {
        sh(foo.set_);
        render(",");
      }
      foo = foo.listset_;
    }
  }

  private static void sh(Swag.Absyn.Selection foo)
  {
    if (foo instanceof Swag.Absyn.SelTake)
    {
       Swag.Absyn.SelTake _seltake = (Swag.Absyn.SelTake) foo;
       render("(");
       render("SelTake");
       sh(_seltake.location_);
       sh(_seltake.part_);
       sh(_seltake.set_);
       render(")");
    }
  }

  private static void sh(Swag.Absyn.Location foo)
  {
    if (foo instanceof Swag.Absyn.LTop)
    {
       Swag.Absyn.LTop _ltop = (Swag.Absyn.LTop) foo;
       render("LTop");
    }
    if (foo instanceof Swag.Absyn.LBottom)
    {
       Swag.Absyn.LBottom _lbottom = (Swag.Absyn.LBottom) foo;
       render("LBottom");
    }
  }

  private static void sh(Swag.Absyn.Part foo)
  {
    if (foo instanceof Swag.Absyn.PInt)
    {
       Swag.Absyn.PInt _pint = (Swag.Absyn.PInt) foo;
       render("(");
       render("PInt");
       sh(_pint.integer_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.PPerc)
    {
       Swag.Absyn.PPerc _pperc = (Swag.Absyn.PPerc) foo;
       render("(");
       render("PPerc");
       sh(_pperc.integer_);
       render(")");
    }
    if (foo instanceof Swag.Absyn.PFrac)
    {
       Swag.Absyn.PFrac _pfrac = (Swag.Absyn.PFrac) foo;
       render("(");
       render("PFrac");
       sh(_pfrac.integer_1);
       sh(_pfrac.integer_2);
       render(")");
    }
  }


  private static void pp(Integer n, int _i_) { buf_.append(n); buf_.append(" "); }
  private static void pp(Double d, int _i_) { buf_.append(d); buf_.append(" "); }
  private static void pp(String s, int _i_) { buf_.append(s); buf_.append(" "); }
  private static void pp(Character c, int _i_) { buf_.append("'" + c.toString() + "'"); buf_.append(" "); }
  private static void sh(Integer n) { buf_.append(n); }
  private static void sh(Double d) { buf_.append(d); }
  private static void sh(Character c) { buf_.append(c); }
  private static void sh(String s) { printQuoted(s); }
  private static void printQuoted(String s) { buf_.append("\"" + s + "\""); }
  private static void indent()
  {
    int n = _n_;
    while (n > 0)
    {
      buf_.append(" ");
      n--;
    }
  }
  private static void backup()
  {
   String s = buf_.toString();

     if (s.substring(buf_.length() - 1, buf_.length()).equals(" ")) {
       buf_.setCharAt(buf_.length() - 1, '\"');
       buf_.setLength(buf_.length() - 1);
     }
  }
  private static int _n_ = 0;
  private static StringBuffer buf_ = new StringBuffer(INITIAL_BUFFER_SIZE);
}

