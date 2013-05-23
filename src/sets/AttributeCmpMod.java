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
    public SortedSet<E> apply(SortedSet<E> set) {
        SortedSet<E> ret = new TreeSet<E>();
        for (E e : mod.apply(set)) {
            if (op.apply(((Number)((model.Player<?>)e).get(str)),value)) {
                ret.add(e);
            }
        }
        System.out.println(ret);
        return ret;
    }
}
