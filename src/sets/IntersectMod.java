package sets;

import java.util.*;

public class IntersectMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod1;
    private final SetModifier<E> mod2;

    public IntersectMod(SetModifier<E> mod1, SetModifier<E> mod2) {
        this.mod1 = mod1;
        this.mod2 = mod2;
    }

    public SortedSet<E> apply(SortedSet<? extends E> set, Comparator<? super E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        SortedSet<E> s2 = mod2.apply(set,comp);
        for (E e1 : mod1.apply(set,comp)) {
            if (s2.contains(e1)) {
                ret.add(e1);
            }
        }
        return ret;
    }
}
