package sets;

import java.util.*;

public class IdentityMod<E> implements SetModifier<E> {
    public SortedSet<E> apply(SortedSet<? extends E> set, Comparator<? super E> comp) {
        if (comp == null) {
            return new TreeSet(set);
        } else {
            TreeSet<E> ret = new TreeSet<>(comp);
            ret.addAll(set);
            return ret;
        }
    }
}
