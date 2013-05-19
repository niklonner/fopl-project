package util;

import java.util.*;

public class TopMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod;
    private final int num;

    public TopMod(int num) {
        this(num, new IdentityMod());
    }

    public TopMod(int num, SetModifier<E> mod) {
        this.num = num;
        this.mod = mod;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<E> set) {
        SortedSet<E> ret = new TreeSet<E>();
        Iterator<E> it = mod.apply(set).iterator();
        for (int i=0;i<num && it.hasNext();i++) {
            ret.add(it.next());
        }
        return ret;
    }
}
