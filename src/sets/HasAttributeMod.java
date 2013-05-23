package sets;

import java.util.*;

public class HasAttributeMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod;
    private final String str;

    public HasAttributeMod(String str, SetModifier<E> mod) {
        this.str = str;
        this.mod = mod;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<E> set, Comparator<E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        for (E e : mod.apply(set,comp)) {
            if (((model.Player<?>)e).attributeIsSet(str)) {
                ret.add(e);
            }
        }
        return ret;
    }
}

