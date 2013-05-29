package sets;

import java.util.*;

public interface SetModifier<E> {

    // should return a NEW set
    public SortedSet<E> apply(SortedSet<? extends E> set, Comparator<? super E> comp);
    
}
