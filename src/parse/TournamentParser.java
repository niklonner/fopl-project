package parse;

import Swag.*;
import Swag.Absyn.*;

import model.*;
import util.*;
import parse.*;
import sets.*;

import java_cup.runtime.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Collections;
import java.util.Comparator;

import java.lang.reflect.*;

/**
 * Responsible for traversing a Swag parse-tree and creating the corresponding
 * tournament.
 * Most of the work is delegated to the private "Worker"-class.
 */
public class TournamentParser {
    private List<String> packages;

    public TournamentParser() {
        this(java.util.Collections.<String>emptyList());
    }

    public TournamentParser(List<String> packages) {
        this.packages = new LinkedList<>(packages);
        this.packages.add("model");
    }

    /**
     * For simple command-line testing
     */
    public static void main(String args[]) throws Exception {
        System.out.println("\nRunning\n");
        Swag.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(args[0]);
        if(parse_tree != null) {
            Worker visitor = new Worker(java.util.Arrays.asList("model"));
            parse_tree.accept(visitor);
            for (model.SubTournament<?> t : visitor.superTournament) {
                t.startBuild();
            }
            System.out.println("players:");
            for (model.SubTournament<?> t : visitor.superTournament) {
                System.out.println(t.getId() + " " + t.getPlayers());
            }
        }
    }

    /*    public void parse(String path) throws ContextException {
        try {
            Swag.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(path);
            Worker visitor = new Worker(packages);
            if(parse_tree != null) {
                parse_tree.accept(visitor);
                //            visitor.subTournaments.get(0).startBuild();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        }*/

    public <T> Tournament<T> parse(String path, RandomGenerator<T> rnd, Comparator<Player<T>> cmp, PrettyPrinterScore<T> pps) throws ContextException, FileNotFoundException {
        Swag.Absyn.Prog parse_tree;
        parse_tree = BasicParser.parseTournamentFile(path);
        Worker visitor = new Worker(packages);
        visitor.setComparator(cmp);
        visitor.setRandomGenerator(rnd);
        visitor.setPrettyPrinter(pps);
        if(parse_tree != null) {
            parse_tree.accept(visitor);
            for (model.SubTournament<?> t : visitor.superTournament) {
                t.startBuild();
            }
            return (Tournament) visitor.superTournament;
        }
        return null;
    }

    public List<model.SubTournament<?>> parseString(String sourceCode) throws ContextException {
        List<model.SubTournament<?>> subt;
        Swag.Absyn.Prog parse_tree = BasicParser.parseTournamentString(sourceCode);
        Worker visitor = new Worker(packages);
        if(parse_tree != null) {
            parse_tree.accept(visitor);
        }
        //        return visitor.subTournaments;
        List<model.SubTournament<?>> ret = new ArrayList<>();
        for (model.SubTournament<?> t : visitor.superTournament) {
            ret.add(t);
            t.startBuild();
        }
        return ret;
    }

    /**
     * Actually handles the tree-traversal.
     */
    public static class Worker implements Visitor {
        Deque<Object> stack = new ArrayDeque<>();

        Tournament<Integer> superTournament = new Tournament<>();

        model.SubTournament.Builder<?,Integer> builder;
        int nrParam;        // negative when not in parameter-collecting mode, non-negative otherwise
        int nrUnionParam;

        ReflectionHelper rh = new ReflectionHelper();

        Comparator comp;
        RandomGenerator rnd;
        PrettyPrinterScore pps;

        List<String> packages;

        public Worker(List<String> packages) {
            this.packages = packages;
        }

        public void setComparator(Comparator cmp) {
            comp = cmp;
        }

        public void setRandomGenerator(RandomGenerator rnd) {
            this.rnd = rnd;
        }

        public void setPrettyPrinter(PrettyPrinterScore pps) {
            this.pps = pps;
        }

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

            boolean success = false;

            for (String pkg : packages) {
                StringBuilder clazz = new StringBuilder();
                clazz.append(pkg);
                clazz.append(".");
                clazz.append(rh.toClassCamelCase(subtournament.ident_));
                clazz.append("$Builder");

                try {
                    builder = (model.SubTournament.Builder<?,Integer>)Class.forName(clazz.toString()).newInstance();
                    success = true;
                    break;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                }
            }
            if (!success) {
                System.err.println("Unrecognizable subtournament type \"" +
                                   subtournament.ident_ + "\".");
                System.exit(1);
            }

            if (comp != null) {
                builder.setComparator(comp);
            }
            if (rnd != null) {
                builder.setRandomGenerator(rnd);
            }
            if (pps != null) {
                builder.setPrettyPrinter(pps);
            }

            if (subtournament.liststmt_ != null) {subtournament.liststmt_.accept(this);}

            builder.setTournament(superTournament);
            model.SubTournament<Integer> st = builder.getSubTournament();
            st.setName(subtournament.string_);
            superTournament.addSubTournament(subtournament.string_,st);
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
            nrParam = -1;  // turn off parameter-collecting mode
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
                if (nrParam > -1) { // increase only if in parameter-collecting mode
                    nrParam++;
                }
                nrUnionParam++;
                listexp.exp_.accept(this);
                listexp = listexp.listexp_;
            }
        }
        public void visitExp(Swag.Absyn.Exp exp) {} //abstract class
        public void visitEunion(Swag.Absyn.Eunion eunion)
        {
            int nrParamTemp = nrParam;
            nrParam = -1; // turn off parameter-collecting mode

            int nrUnionParamTemp = nrUnionParam; // store current number (in case of nested unions)
            nrUnionParam = 0;

            if (eunion.listexp_ != null) {eunion.listexp_.accept(this);}

            List<Object> os = pop(nrUnionParam, stack);
            List<SetModifier<Player<?>>> ss = new LinkedList<>();
            for (Object o : os) {
                ss.add(setModifierReplaceKeywords(o));
            }
            SetModifier<Player<?>> sm = new UnionMod<>(ss);
            stack.push(sm);

            nrParam = nrParamTemp; // turn parameter-collecting mode on again

            nrUnionParam = nrUnionParamTemp; // restore
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

            Object y = stack.pop();
            Object x = stack.pop();
            if (y instanceof String) {
                SetModifier<Player<?>> sm =  new HasAttributeMod<Player<?>>((String)y,setModifierReplaceKeywords(x));
                stack.push(sm);
            } else {
                throw new UnsupportedOperationException();
            }

        }


        public void visitEfolcmp(Swag.Absyn.Efolcmp efolcmp)
        {
            /* Code For Efolcmp Goes Here */

            efolcmp.exp_1.accept(this);
            visitIdent(efolcmp.ident_);
            efolcmp.cmpop_.accept(this);
            efolcmp.exp_2.accept(this);

            Object w = stack.pop();
            Object z = stack.pop();
            Object y = stack.pop();
            Object x = stack.pop();

            if (y instanceof String && w instanceof Number) {
                SetModifier<Player<?>> sm =  new AttributeCmpMod<Player<?>>((String)y, (Operator) z, ((Number)w).doubleValue(),setModifierReplaceKeywords(x));
                stack.push(sm);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public void visitEdifference(Swag.Absyn.Edifference edifference)
        {
            edifference.exp_1.accept(this);
            edifference.exp_2.accept(this);

            Object y = stack.pop();
            Object x = stack.pop();

            SetModifier<Player<?>> sm =  new DifferenceMod<Player<?>>(setModifierReplaceKeywords(x),setModifierReplaceKeywords(y));
            stack.push(sm);
        }


        public void visitEintersect(Swag.Absyn.Eintersect eintersect)
        {
            eintersect.exp_1.accept(this);
            eintersect.exp_2.accept(this);

            Object y = stack.pop();
            Object x = stack.pop();

            SetModifier<Player<?>> sm =  new IntersectMod<Player<?>>(setModifierReplaceKeywords(x),setModifierReplaceKeywords(y));
            stack.push(sm);

        }
        public void visitENotIntersect(Swag.Absyn.ENotIntersect enotintersect)
        {
            /* Code For ENotIntersect Goes Here */

            enotintersect.exp_1.accept(this);
            enotintersect.exp_2.accept(this);

            Object y = stack.pop();
            Object x = stack.pop();

            SetModifier<Player<?>> sm =  new NotIntersectMod<Player<?>>(setModifierReplaceKeywords(x),setModifierReplaceKeywords(y));
            stack.push(sm);

        }

        public void visitETop(Swag.Absyn.ETop etop)
        {
            visitInteger(etop.integer_);
            etop.exp_.accept(this);

            Object y = stack.pop();
            Object x = stack.pop();

            if (x instanceof Integer) {
                SetModifier<Player<?>> sm =  new TopMod<Player<?>>((int)x,setModifierReplaceKeywords(y));
                stack.push(sm);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public void visitEBottom(Swag.Absyn.EBottom ebottom)
        {
            visitInteger(ebottom.integer_);
            ebottom.exp_.accept(this);

            Object y = stack.pop();
            Object x = stack.pop();

            if (x instanceof Integer) {
                SetModifier<Player<?>> sm =  new BottomMod<Player<?>>((int)x,setModifierReplaceKeywords(y));
                stack.push(sm);
            } else {
                throw new UnsupportedOperationException();
            }
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
        public void visitEString(Swag.Absyn.EString estring) {
            visitString(estring.string_);
        }

        public void visitIdent(String i) {
            //TODO: Make variables work. (Possibly using a map.)
            stack.push(i);
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

        public void visitCmpOp(Swag.Absyn.CmpOp cmpop) {} //abstract class
        public void visitCOne(Swag.Absyn.COne cone)
        {
            stack.push(new NeOp());
        }
        public void visitCOeq(Swag.Absyn.COeq coplus)
        {
            stack.push(new EqOp());
        }
        public void visitCOlt(Swag.Absyn.COlt colt)
        {
            stack.push(new LtOp());
        }
        public void visitCOle(Swag.Absyn.COle cole)
        {
            stack.push(new LeOp());
        }
        public void visitCOgt(Swag.Absyn.COgt cogt)
        {
            stack.push(new GtOp());
        }
        public void visitCOge(Swag.Absyn.COge coge)
        {
            stack.push(new GeOp());
        }

        /**
         * Retrieves a number of objects from the stack as an Array.
         */
        private <T extends Queue<?>> List<Object> pop(int number, T queue) {
            List<Object> list = new ArrayList<>();
            for(int i = 0; i < number; i++) {
                list.add(queue.remove());
            }
            Collections.reverse(list);
            return list;
        }

        // takes care of reserved words, e.g. replaces "all" with
        // an IdentityMod
        private SetModifier<Player<?>> setModifierReplaceKeywords(Object o) {
            if (o instanceof SetModifier<?>) {
                return (SetModifier<Player<?>>) o;
            } else if(o instanceof String && ((String)o).equalsIgnoreCase("all")) {
                return new IdentityMod<>();
            }
            throw new UnsupportedOperationException("set modifier expected");
        }
    }
}
