package sets;

import java.util.*;

public class IdentityMod<E> implements SetModifier<E> {
    public SortedSet<E> apply(SortedSet<E> set) {
        return new TreeSet(set);
    }
}
