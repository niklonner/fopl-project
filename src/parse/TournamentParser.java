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
            for (int i=1;i<args.length;i++) {
                visitor.superTournament.findSubTournament(args[i]).startBuild();
            }
        }
    }

    public void parse(String path) throws ContextException {
        try {
            Swag.Absyn.Prog parse_tree = BasicParser.parseTournamentFile(path);
            Worker visitor = new Worker(packages);
            if(parse_tree != null) {
                parse_tree.accept(visitor);
                visitor.subTournaments.get(0).startBuild();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public <T> Tournament<T> parse(String path, RandomGenerator<T> rnd, Comparator<Player<T>> cmp) throws ContextException {
        Swag.Absyn.Prog parse_tree;
        try {
            parse_tree = BasicParser.parseTournamentFile(path);
        } catch (FileNotFoundException e) {
            return null;
        }
        Worker visitor = new Worker(packages);
        visitor.setComparator(cmp);
        visitor.setRandomGenerator(rnd);
        if(parse_tree != null) {
            parse_tree.accept(visitor);
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
            /*subt =*/ visitor.subTournaments.get(0).startBuild();
        }
        //        return visitor.subTournaments;
        return null;
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

        Map<String,model.SubTournament<?>> subTournaments = new TreeMap<>();

        Tournament<Integer> superTournament = new Tournament<>();
            
        model.SubTournament.Builder<?,Integer> builder;
        int nrParam;
        int nrUnionParam;

        ReflectionHelper rh = new ReflectionHelper();

        Comparator comp;
        RandomGenerator rnd;

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
            
            if (subtournament.liststmt_ != null) {subtournament.liststmt_.accept(this);}

            builder.setTournament(superTournament);
            superTournament.addSubTournament(subtournament.string_,builder.getSubTournament());
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
                nrUnionParam++;
                listexp.exp_.accept(this);
                listexp = listexp.listexp_;
            }
        }
        public void visitExp(Swag.Absyn.Exp exp) {} //abstract class
        public void visitEunion(Swag.Absyn.Eunion eunion)
        {
            /* Code For Eunion Goes Here */

            nrUnionParam = 0;
            
            if (eunion.listexp_ != null) {eunion.listexp_.accept(this);}
            
            List<Object> os = pop(nrUnionParam, stack);
            List<SetModifier<Player<?>>> ss = new LinkedList<>();
            for (Object o : os) {
                ss.add(setModifierReplaceKeywords(o));
            }
            SetModifier<Player<?>> sm = new UnionMod<>(ss);
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
        
        public void visitEintersect(Swag.Absyn.Eintersect eintersect)
        {
            /* Code For Eintersect Goes Here */

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
        public void visitCOeq(Swag.Absyn.COeq coplus)
        {
            /* Code For COplus Goes Here */
            stack.push(new EqOp());

        }
        public void visitCOlt(Swag.Absyn.COlt colt)
        {
            /* Code For COlt Goes Here */
            stack.push(new LtOp());

        }
        public void visitCOle(Swag.Absyn.COle cole)
        {
            /* Code For COle Goes Here */
            stack.push(new LeOp());

        }
        public void visitCOgt(Swag.Absyn.COgt cogt)
        {
            /* Code For COgt Goes Here */
            stack.push(new GtOp());

        }
        public void visitCOge(Swag.Absyn.COge coge)
        {
            /* Code For COge Goes Here */
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
