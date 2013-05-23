package sets;

import java.util.*;

public interface SetModifier<E> {

    // should return a NEW set
    public SortedSet<E> apply(SortedSet<E> set, Comparator<E> comp);
    
}
