JAVAC = javac
JAVAC_JAVA_CFLAGS =

JAVA = java

CUP = java_cup.Main
CUPFLAGS = -nopositions -expect 100

JLEX = JLex.Main

LATEX = latex
DVIPS = dvips

all: test Swag.ps

test: absyn Swag/Visitor.class Swag/Visitable.class Swag/Yylex.class Swag/PrettyPrinter.class Swag/Test.class Swag/Absyn/parser.class Swag/Absyn/sym.class

.PHONE: absyn

absyn: Swag/Absyn/Program.class Swag/Absyn/Prog.class Swag/Absyn/Tournament.class Swag/Absyn/ListTournament.class Swag/Absyn/ListStmt.class Swag/Absyn/Stmt.class Swag/Absyn/SExp.class Swag/Absyn/SAss.class Swag/Absyn/SSend.class Swag/Absyn/SimpleExp.class Swag/Absyn/SimpExpStr.class Swag/Absyn/SimpExpInt.class Swag/Absyn/Exp.class Swag/Absyn/ESimp.class Swag/Absyn/ESet.class Swag/Absyn/Set.class Swag/Absyn/SDiff.class Swag/Absyn/SUnionP.class Swag/Absyn/SIntersect.class Swag/Absyn/SNot.class Swag/Absyn/SHasAttr.class Swag/Absyn/SVar.class Swag/Absyn/SSel.class Swag/Absyn/SUnion.class Swag/Absyn/StringComparison.class Swag/Absyn/SCmp.class Swag/Absyn/ComparisonOp.class Swag/Absyn/CmpOpEq.class Swag/Absyn/CmpOpLt.class Swag/Absyn/CmpOpGt.class Swag/Absyn/CmpOpLeq.class Swag/Absyn/CmpOpGeq.class Swag/Absyn/Player.class Swag/Absyn/ListSet.class Swag/Absyn/Selection.class Swag/Absyn/SelTake.class Swag/Absyn/Location.class Swag/Absyn/LTop.class Swag/Absyn/LBottom.class Swag/Absyn/Part.class Swag/Absyn/PInt.class Swag/Absyn/PPerc.class Swag/Absyn/PFrac.class

Swag/Absyn/%.class: Swag/Absyn/%.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Absyn/$*.java

Swag/Visitable.class: Swag/Visitable.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Visitable.java

Swag/Visitor.class: Swag/Visitor.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Visitor.java

Swag/Swag.lex.java: Swag/Swag.lex
	${JAVA} ${JLEX} Swag/Swag.lex

Swag/Absyn/sym.java: Swag/Swag.cup
	${JAVA} ${CUP} ${CUPFLAGS} Swag/Swag.cup ; mv sym.java parser.java Swag/Absyn/

Swag/Absyn/parser.java: Swag/Swag.cup
	${JAVA} ${CUP} ${CUPFLAGS} Swag/Swag.cup ; mv sym.java parser.java Swag/Absyn/

Swag/Yylex.class: Swag/Swag.lex.java Swag/Absyn/sym.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Swag.lex.java

Swag/Absyn/sym.class: Swag/Absyn/sym.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Absyn/sym.java

Swag/Absyn/parser.class: Swag/Absyn/parser.java Swag/Absyn/sym.java
	${JAVAC} ${JAVA_CFLAGS} Swag/Absyn/parser.java

Swag/PrettyPrinter.class: Swag/PrettyPrinter.java
	${JAVAC} ${JAVA_CFLAGS} Swag/PrettyPrinter.java

Swag/Test.class: Swag/Test.java Swag/PrettyPrinter.class Swag/Yylex.class Swag/Absyn/parser.class Swag/Absyn/sym.class
	${JAVAC} ${JAVA_CFLAGS} Swag/Test.java

Swag.dvi: Swag.tex
	${LATEX} Swag.tex

Swag.ps: Swag.dvi
	${DVIPS} Swag.dvi -o Swag.ps

clean:
	 rm -f Swag/Absyn/Program.class Swag/Absyn/Prog.class Swag/Absyn/Tournament.class Swag/Absyn/ListTournament.class Swag/Absyn/ListStmt.class Swag/Absyn/Stmt.class Swag/Absyn/SExp.class Swag/Absyn/SAss.class Swag/Absyn/SSend.class Swag/Absyn/SimpleExp.class Swag/Absyn/SimpExpStr.class Swag/Absyn/SimpExpInt.class Swag/Absyn/Exp.class Swag/Absyn/ESimp.class Swag/Absyn/ESet.class Swag/Absyn/Set.class Swag/Absyn/SDiff.class Swag/Absyn/SUnionP.class Swag/Absyn/SIntersect.class Swag/Absyn/SNot.class Swag/Absyn/SHasAttr.class Swag/Absyn/SVar.class Swag/Absyn/SSel.class Swag/Absyn/SUnion.class Swag/Absyn/StringComparison.class Swag/Absyn/SCmp.class Swag/Absyn/ComparisonOp.class Swag/Absyn/CmpOpEq.class Swag/Absyn/CmpOpLt.class Swag/Absyn/CmpOpGt.class Swag/Absyn/CmpOpLeq.class Swag/Absyn/CmpOpGeq.class Swag/Absyn/Player.class Swag/Absyn/ListSet.class Swag/Absyn/Selection.class Swag/Absyn/SelTake.class Swag/Absyn/Location.class Swag/Absyn/LTop.class Swag/Absyn/LBottom.class Swag/Absyn/Part.class Swag/Absyn/PInt.class Swag/Absyn/PPerc.class Swag/Absyn/PFrac.class
	 rm -f .dvi Swag.aux Swag.log Swag.ps  *.class Makefile

distclean:
	 rm -f Swag/Absyn/Program.java Swag/Absyn/Prog.java Swag/Absyn/Tournament.java Swag/Absyn/ListTournament.java Swag/Absyn/ListStmt.java Swag/Absyn/Stmt.java Swag/Absyn/SExp.java Swag/Absyn/SAss.java Swag/Absyn/SSend.java Swag/Absyn/SimpleExp.java Swag/Absyn/SimpExpStr.java Swag/Absyn/SimpExpInt.java Swag/Absyn/Exp.java Swag/Absyn/ESimp.java Swag/Absyn/ESet.java Swag/Absyn/Set.java Swag/Absyn/SDiff.java Swag/Absyn/SUnionP.java Swag/Absyn/SIntersect.java Swag/Absyn/SNot.java Swag/Absyn/SHasAttr.java Swag/Absyn/SVar.java Swag/Absyn/SSel.java Swag/Absyn/SUnion.java Swag/Absyn/StringComparison.java Swag/Absyn/SCmp.java Swag/Absyn/ComparisonOp.java Swag/Absyn/CmpOpEq.java Swag/Absyn/CmpOpLt.java Swag/Absyn/CmpOpGt.java Swag/Absyn/CmpOpLeq.java Swag/Absyn/CmpOpGeq.java Swag/Absyn/Player.java Swag/Absyn/ListSet.java Swag/Absyn/Selection.java Swag/Absyn/SelTake.java Swag/Absyn/Location.java Swag/Absyn/LTop.java Swag/Absyn/LBottom.java Swag/Absyn/Part.java Swag/Absyn/PInt.java Swag/Absyn/PPerc.java Swag/Absyn/PFrac.java Swag/Absyn/Program.class Swag/Absyn/Prog.class Swag/Absyn/Tournament.class Swag/Absyn/ListTournament.class Swag/Absyn/ListStmt.class Swag/Absyn/Stmt.class Swag/Absyn/SExp.class Swag/Absyn/SAss.class Swag/Absyn/SSend.class Swag/Absyn/SimpleExp.class Swag/Absyn/SimpExpStr.class Swag/Absyn/SimpExpInt.class Swag/Absyn/Exp.class Swag/Absyn/ESimp.class Swag/Absyn/ESet.class Swag/Absyn/Set.class Swag/Absyn/SDiff.class Swag/Absyn/SUnionP.class Swag/Absyn/SIntersect.class Swag/Absyn/SNot.class Swag/Absyn/SHasAttr.class Swag/Absyn/SVar.class Swag/Absyn/SSel.class Swag/Absyn/SUnion.class Swag/Absyn/StringComparison.class Swag/Absyn/SCmp.class Swag/Absyn/ComparisonOp.class Swag/Absyn/CmpOpEq.class Swag/Absyn/CmpOpLt.class Swag/Absyn/CmpOpGt.class Swag/Absyn/CmpOpLeq.class Swag/Absyn/CmpOpGeq.class Swag/Absyn/Player.class Swag/Absyn/ListSet.class Swag/Absyn/Selection.class Swag/Absyn/SelTake.class Swag/Absyn/Location.class Swag/Absyn/LTop.class Swag/Absyn/LBottom.class Swag/Absyn/Part.class Swag/Absyn/PInt.class Swag/Absyn/PPerc.class Swag/Absyn/PFrac.class
	 rmdir Absyn
	 rm -f Swag.tex Swag.dvi Swag.aux Swag.log Swag.ps Swag.lex Swag.cup Swag.lex.java sym.java parser.java Visitor.java Visitable.java VisitSkel.java PrettyPrinter.java Skeleton.java Test.java *.class Makefile

