package sets;

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
    public SortedSet<E> apply(SortedSet<E> set, Comparator<E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        Iterator<E> it = mod.apply(set,comp).iterator();
        for (int i=0;i<num && it.hasNext();i++) {
            ret.add(it.next());
        }
        return ret;
    }
}
