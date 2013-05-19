package util;

import java.util.SortedSet;

public interface SetModifier<E> {

    // should return a NEW set
    public SortedSet<E> apply(SortedSet<E> set);
    
}