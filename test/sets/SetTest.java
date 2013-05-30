package sets;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

import model.*;

public class SetTest {
    private DummyMod[] sets;
    private static final SortedSet<Player<?>> DUMMY_SET;
    private static final int SETS = 10;
    private static final Random rnd = new Random();

    static {
        DUMMY_SET = new TreeSet<>();
        for (int i=0;i<SETS;i++) {
            DUMMY_SET.add(new Player<Integer>());
        }
    }
    
    public SetTest() {
        sets = new DummyMod[10];
        List<Integer> order = new ArrayList<>();
        for (int i=0;i<sets.length;i++) {
            order.add(i);
        }
        Collections.shuffle(order);
        int j = 0;
        for (Integer i : order) {
            sets[j++] = makePlayers(i);
        }
        /*        for (SortedSet<Player<?>> ss : sets) {
            System.out.println(ss);
            }*/
    }

    private DummyMod makePlayers(int number) {
        SortedSet<Player<Integer>> players = new TreeSet<>();
        for (int i=0;i<number;i++) {
            players.add(new Player());
        }
        return new DummyMod(players);
    }

    private static class DummyMod implements SetModifier<Player<Integer>> {
        SortedSet<Player<Integer>> ps;
        
        public DummyMod(SortedSet<Player<Integer>> ps) {
            this.ps = ps;
        }

        public SortedSet<Player<Integer>> apply(SortedSet<? extends Player<Integer>> ignore, Comparator<? super Player<Integer>> ignore2) {
            return ps;
        }
    }
    
    @Test
    public void testIdentityMod() {
        IdentityMod<Player<?>> test = new IdentityMod<>();
        for (DummyMod d : sets) {
            SortedSet<Player<?>> res = test.apply(d.ps,null);
            for (Player<?> p : res) {
                assertTrue(d.ps.contains(p));
            }
            for (Player<?> p : d.ps) {
                assertTrue(res.contains(p));
            }
        }
    }

    @Test
    public void testTopMod() {
        testBTMod(true);
    }

    @Test
    public void testBottomMod() {
        testBTMod(false);
    }

    
    public void testBTMod(boolean top) {
        for (DummyMod d : sets) {
            int n = d.ps.size() == 0 ? 0 : rnd.nextInt(d.ps.size());
            SetModifier<Player<?>> test = top ? new TopMod(n,d) : new BottomMod(n,d);
            SortedSet<Player<?>> res = test.apply(DUMMY_SET,null);
             for (Player<?> p : res) {
                assertTrue(d.ps.contains(p));
            }
            int i=0;
            int numNot = d.ps.size()-n;
            for (Player<?> p : d.ps) {
                if ((i<numNot && !top) || (i>=n && top)) {
                    assertFalse(res.contains(p));
                } else {
                    assertTrue(res.contains(p));
                }
                i++;
            }
        }
    }

    @Test
    public void testHasAttributeMod() {
        final String TEST = "TEST";
        for (DummyMod d : sets) {
            for (Player<?> p : d.ps) {
                if (rnd.nextInt(2) == 0) {
                    p.set(TEST,true);
                }
            }
            HasAttributeMod<Player<?>> test = new HasAttributeMod<>(TEST,(SetModifier)d);
            SortedSet<Player<?>> res = test.apply(DUMMY_SET,null);
            for (Player<?> p : res) {
                assertTrue(p.attributeIsSet(TEST));
                assertTrue(d.ps.contains(p));
            }
            for (Player<?> p : d.ps) {
                if (p.attributeIsSet(TEST)) {
                    assertTrue(res.contains(p));
                } else {
                    assertFalse(res.contains(p));
                }
            }
            for (Player<?> p : d.ps) {
                p.set(TEST,null);
            }
        }
    }

    @Test
    public void testIntersectMod() {
        testNIMod(true);
    }

    @Test
    public void testNotIntersectMod() {
        testNIMod(false);
    }
    
    private void testNIMod(boolean intersect) {
        for (int i=0;i<SETS;i++) {
            for (int j=0;j<SETS;j++) {
                SetModifier<Player<?>> test = intersect ? new IntersectMod<Player<?>>((SetModifier)sets[i],(SetModifier)sets[j]) : new NotIntersectMod<Player<?>>((SetModifier)sets[i],(SetModifier)sets[j]);
                SortedSet<Player<?>> res = test.apply(DUMMY_SET,null);
                for (Player<?> p : res) {
                    if (intersect) {
                        assertTrue(sets[i].ps.contains(p) && sets[j].ps.contains(p));
                    } else {
                        assertFalse(sets[i].ps.contains(p) && sets[j].ps.contains(p));
                    }
                }
                for (Player<?> p : sets[i].ps) {
                    if ((sets[j].ps.contains(p) && intersect) || (!sets[j].ps.contains(p) && !intersect)) {
                        assertTrue(res.contains(p));
                    } else {
                        assertFalse(res.contains(p));
                    }
                }
                for (Player<?> p : sets[j].ps) {
                    if ((sets[i].ps.contains(p) && intersect) || (!sets[i].ps.contains(p) && !intersect)) {
                        assertTrue(res.contains(p));
                    } else {
                        assertFalse(res.contains(p));
                    }
                }
            }
        }
    }

    @Test
    public void testUnionMod() {
        List<Integer> order = new ArrayList<>();
        for (int i=0;i<SETS;i++) {
            order.add(i);
        }
        for (int i=0;i<SETS*50;i++) {
            int n = rnd.nextInt(SETS);
            List<SetModifier<Player<?>>> ls = new LinkedList<>();
            Collections.shuffle(order);
            Iterator<Integer> it = order.iterator();
            for (int j=0;j<n;j++) {
                ls.add((SetModifier)sets[it.next()]);
            }
            UnionMod<Player<?>> test = new UnionMod<Player<?>>(ls);
            SortedSet<Player<?>> res = test.apply(DUMMY_SET,null);
            for (Player<?> p : res) {
                boolean inSomeList = false;
                for (SetModifier ss : ls) {
                    DummyMod d = (DummyMod)ss;
                    if (d.ps.contains(p)) {
                        inSomeList = true;
                        break;
                    }
                }
                assertTrue(inSomeList);
            }
            for (SetModifier ss : ls) {
                DummyMod d = (DummyMod)ss;
                for (Player<?> p : d.ps) {
                    assertTrue(res.contains(p));
                }
            }
        }
    }

    @Test
    public void testAttributeCmpMod() {
        EqOp eqop = new EqOp();
        GeOp geop = new GeOp();
        GtOp gtop = new GtOp();
        LeOp leop = new LeOp();
        LtOp ltop = new LtOp();
        Operator[] ops = {eqop, geop, gtop, leop, ltop};
        final String TEST = "TEST";
        for (DummyMod d : sets) {
            for (Player<?> p : d.ps) {
                if (rnd.nextInt(2) == 0) {
                    p.set(TEST, rnd.nextInt(100) - 50);
                }
            }
        }
        for (Operator op : ops) {
            for (DummyMod d : sets) {
                double val = rnd.nextDouble();
                AttributeCmpMod<Player<?>> test = new AttributeCmpMod(TEST, op, val, d);
                SortedSet<Player<?>> res = test.apply(DUMMY_SET, null);
                for (Player<?> p : res) {
                    assertTrue(d.ps.contains(p));
                    assertTrue(op.apply((Number)p.get(TEST),val));
                }
                for (Player<?> p : d.ps) {
                    if (op.apply((Number)p.get(TEST),val)) {
                        assertTrue(res.contains(p));
                    } else {
                        assertFalse(res.contains(p));
                    }
                }
            }
        }
        for (DummyMod d : sets) {
            for (Player<?> p : d.ps) {
                p.set(TEST, null);
            }
        }
    }
}