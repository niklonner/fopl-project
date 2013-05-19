package sets;

import java.util.*;

public class BottomMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod;
    private final int num;

    public BottomMod(int num) {
        this(num, new IdentityMod());
    }

    public BottomMod(int num, SetModifier<E> mod) {
        this.num = num;
        this.mod = mod;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<E> set) {
        SortedSet<E> ret = new TreeSet<E>();
        SortedSet<E> s = mod.apply(set);
        Iterator<E> it = s.iterator();
        for (int i=0;i<s.size()-num;i++) {
            it.next();
        }
        for (int i=0;i<num && it.hasNext();i++) {
            ret.add(it.next());
        }
        return ret;
    }
}
