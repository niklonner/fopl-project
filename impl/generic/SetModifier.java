package generic;

import java.util.SortedSet;

public interface SetModifier<E> {
    public SortedSet<E> apply();
}