package sets;

import java.util.*;

public class NotIntersectMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod1;
    private final SetModifier<E> mod2;

    public NotIntersectMod(SetModifier<E> mod1, SetModifier<E> mod2) {
        this.mod1 = mod1;
        this.mod2 = mod2;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<E> set, Comparator<E> comp) {
        SortedSet<E> intersect = new IntersectMod<E>(mod1,mod2).apply(set,comp);
        SortedSet<E> ret = new TreeSet<E>(comp);
        for (E e : set) {
            if (!intersect.contains(e)) {
                ret.add(e);
            }
        }
        return ret;
    }
}
