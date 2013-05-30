package sets;

import java.util.*;

public class AttributeCmpMod<E> implements SetModifier<E> {
    private final SetModifier<E> mod;
    private final String str;
    private final Operator op;
    private final Double value;

    public AttributeCmpMod(String str, Operator op, Double value, SetModifier<E> mod) {
        this.str = str;
        this.op = op;
        this.value = value;
        this.mod = mod;
    }

    // returns tom num elements of (perhaps modified) set
    public SortedSet<E> apply(SortedSet<? extends E> set, Comparator<? super E> comp) {
        SortedSet<E> ret = new TreeSet<E>(comp);
        for (E e : mod.apply(set,comp)) {
            if (op.apply(((Number)((model.Player<?>)e).get(str)),value)) {
                ret.add(e);
            }
        }
        return ret;
    }
}
