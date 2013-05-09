
import Swag.*;
import Swag.Absyn.*;

import generic.*;
import util.*;

import java_cup.runtime.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Queue;

import java.lang.reflect.*;

public class TournamentParser {

    /**
     * For simple command-line testing
     */
    public static void main(String args[]) throws Exception {
        System.out.println("\nRunning\n");
        Swag.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(args[0]);
        if(parse_tree != null) {
            Worker visitor = new Worker();
            parse_tree.accept(visitor);
        }
    }


    //public void parse(String path) {
        //Yylex l = null;
        //parser p;

        //try {
            //l = new Yylex(new FileReader(path));
        //}
        //catch(FileNotFoundException e) {
            //System.err.println("Error: File not found: " + path);
            //System.exit(1);
        //}

        //p = new parser(l);

        //try {
            //Play.Absyn.Prog parse_tree = p.pProg();

            //Parser parser = new Parser();
            //parse_tree.accept(parser);
        //} catch(Throwable e) {
            //System.err.println("At line " + String.valueOf(l.line_num()) + ", near \"" + l.buff() + "\" :");
            //System.err.println("     " + e.getMessage());
            //System.exit(1);
        //}
    //}

    public static class Worker implements Visitor {
        Deque<Object> stack = new ArrayDeque<>();

        generic.SubTournament.Builder<?,Integer> builder;
        int nrParam;

        ReflectionHelper rh = new ReflectionHelper();

        public void visitProg(Swag.Absyn.Prog prog) {} //abstract class
        public void visitProgram(Swag.Absyn.Program program) {
            /* Code For Program Goes Here */


            if (program.listsubt_ != null) {program.listsubt_.accept(this);}
        }
        public void visitListSubT(Swag.Absyn.ListSubT listsubt) {
            while(listsubt!= null) {
                /* Code For ListSubT Goes Here */
                listsubt.subt_.accept(this);
                listsubt = listsubt.listsubt_;
            }
        }
        public void visitSubT(Swag.Absyn.SubT subt) {} //abstract class
        public void visitSubTournament(Swag.Absyn.SubTournament subtournament) {
            /* Code For SubTournament Goes Here */

            System.out.println("Making tournament: " + subtournament.string_);
            //subt = new SubTournament(subtournament.string_);


            switch (subtournament.ident_) {
                case "bracket":
                    builder = new Bracket.Builder<Integer>();
                    break;
                default:
                    System.err.println("Unrecognizable subtournament type \"" +
                            subtournament.ident_ + "\".");
                    System.exit(1);
            }

            if (subtournament.liststmt_ != null) {subtournament.liststmt_.accept(this);}
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
        public void visitAssignment(Swag.Absyn.Assignment assignment)
        {
            /* Code For Assignment Goes Here */

            visitIdent(assignment.ident_);
            assignment.exp_.accept(this);
        }

        public void visitParamMethod(Swag.Absyn.ParamMethod parammethod) {
            nrParam = 0;
            if (parammethod.listexp_ != null) {parammethod.listexp_.accept(this);}

            try {
                rh.call(builder, rh.toCamelCase(parammethod.ident_), pop(nrParam, stack));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void visitMethod(Swag.Absyn.Method method) {
            try {
                rh.call(builder, rh.toCamelCase(method.ident_));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        public void visitListExp(Swag.Absyn.ListExp listexp) {
            while(listexp!= null) {
                nrParam++;
                listexp.exp_.accept(this);
                listexp = listexp.listexp_;
            }
        }
        public void visitExp(Swag.Absyn.Exp exp) {} //abstract class
        public void visitEunion(Swag.Absyn.Eunion eunion)
        {
            /* Code For Eunion Goes Here */

            if (eunion.listexp_ != null) {eunion.listexp_.accept(this);}
        }
        public void visitEeq(Swag.Absyn.Eeq eeq)
        {
            /* Code For Eeq Goes Here */

            eeq.exp_1.accept(this);
            eeq.exp_2.accept(this);
        }
        public void visitElt(Swag.Absyn.Elt elt)
        {
            /* Code For Elt Goes Here */

            elt.exp_1.accept(this);
            elt.exp_2.accept(this);
        }
        public void visitElteq(Swag.Absyn.Elteq elteq)
        {
            /* Code For Elteq Goes Here */

            elteq.exp_1.accept(this);
            elteq.exp_2.accept(this);
        }
        public void visitEgt(Swag.Absyn.Egt egt)
        {
            /* Code For Egt Goes Here */

            egt.exp_1.accept(this);
            egt.exp_2.accept(this);
        }
        public void visitEgteq(Swag.Absyn.Egteq egteq)
        {
            /* Code For Egteq Goes Here */

            egteq.exp_1.accept(this);
            egteq.exp_2.accept(this);
        }
        public void visitEadd(Swag.Absyn.Eadd eadd) {
            eadd.exp_1.accept(this);
            eadd.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                if(x instanceof Double || y instanceof Double) {
                    stack.push(((Number) x).doubleValue() + ((Number) y).doubleValue());
                } else {
                    stack.push(((Number) x).intValue() + ((Number) y).intValue());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEsub(Swag.Absyn.Esub esub) {
            esub.exp_1.accept(this);
            esub.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                if(x instanceof Double || y instanceof Double) {
                    stack.push(((Number) x).doubleValue() - ((Number) y).doubleValue());
                } else {
                    stack.push(((Number) x).intValue() - ((Number) y).intValue());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEintersect(Swag.Absyn.Eintersect eintersect)
        {
            /* Code For Eintersect Goes Here */

            eintersect.exp_1.accept(this);
            eintersect.exp_2.accept(this);
        }
        public void visitENotIntersect(Swag.Absyn.ENotIntersect enotintersect)
        {
            /* Code For ENotIntersect Goes Here */

            enotintersect.exp_1.accept(this);
            enotintersect.exp_2.accept(this);
        }
        public void visitEdiv(Swag.Absyn.Ediv ediv) {
            ediv.exp_1.accept(this);
            ediv.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                stack.push(((Number) x).doubleValue() / ((Number) y).doubleValue());
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEmul(Swag.Absyn.Emul emul) {
            emul.exp_1.accept(this);
            emul.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                if(x instanceof Double || y instanceof Double) {
                    stack.push(((Number) x).doubleValue() * ((Number) y).doubleValue());
                } else {
                    stack.push(((Number) x).intValue() * ((Number) y).intValue());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEmod(Swag.Absyn.Emod emod) {
            emod.exp_1.accept(this);
            emod.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                if(x instanceof Double || y instanceof Double) {
                    stack.push(((Number) x).doubleValue() % ((Number) y).doubleValue());
                } else {
                    stack.push(((Number) x).intValue() % ((Number) y).intValue());
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEpow(Swag.Absyn.Epow epow) {
            epow.exp_1.accept(this);
            epow.exp_2.accept(this);
            Object y = stack.pop();
            Object x = stack.pop();

            if(x instanceof Number && y instanceof Number) {
                Double d = Math.pow(((Number) x).doubleValue(), ((Number) y).doubleValue());
                Integer i = d.intValue();
                if(d == i.doubleValue()) {
                    stack.push(i);
                } else {
                    stack.push(d);
                }
            } else {
                throw new UnsupportedOperationException();
            }
        }
        public void visitEfol(Swag.Absyn.Efol efol)
        {
            /* Code For Efol Goes Here */

            efol.exp_.accept(this);
            visitIdent(efol.ident_);
        }
        public void visitEint(Swag.Absyn.Eint eint) {
            visitInteger(eint.integer_);
        }
        public void visitEDouble(Swag.Absyn.EDouble edouble) {
            visitDouble(edouble.double_);
        }
        public void visitEVar(Swag.Absyn.EVar evar) {
            visitIdent(evar.ident_);
        }

        public void visitIdent(String i) {
            //TODO: Make variables work. (Possibly using a map.)
        }
        public void visitInteger(Integer i) {
            stack.push(i);
        }
        public void visitDouble(Double d) {
            stack.push(d);
        }
        public void visitChar(Character c) {
            stack.push(c);
        }
        public void visitString(String s) {
            stack.push(s);
        }

        /**
         * Retrieves a number of objects from the stack as an Array.
         */
        private <T extends Queue<?>> List<Object> pop(int number, T queue) {
            List<Object> list = new ArrayList<>();
            for(int i = 0; i < number; i++) {
                list.add(queue.remove());
            }
            return list;
        }
    }
}
