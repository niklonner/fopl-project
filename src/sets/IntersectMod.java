package sets;

import java.util.*;

public class IntersectMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod1;
    private final SetModifier<E> mod2;

    public IntersectMod(SetModifier<E> mod1, SetModifier<E> mod2) {
        this.mod1 = mod1;
        this.mod2 = mod2;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<E> set, Comparator<E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        for (E e1 : mod1.apply(set,comp)) {
            boolean inOther = false;
            for (E e2 : mod2.apply(set,comp)) {
                if (e1.equals(e2)) {
                    inOther = true;
                }
            }
            if (!inOther) {
                ret.add(e1);
            }
        }
        return ret;
    }
}
