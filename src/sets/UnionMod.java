package sets;

import java.util.*;

public class UnionMod<E> implements SetModifier<E> {
    private final List<SetModifier<E>> mods;

    public UnionMod(List<SetModifier<E>> mods) {
        this.mods = mods;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<? extends E> set, Comparator<? super E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        for (SetModifier<E> mod : mods) {
            for (E e : mod.apply(set,comp)) {
                ret.add(e);
            }
        }
        return ret;
    }
}
