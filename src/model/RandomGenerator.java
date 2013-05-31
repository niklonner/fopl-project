package model;

/* Interface for random generators. Random generators are used to randomize results in the
 * dummy runs made when initializing tournaments.
 */

public interface RandomGenerator<T> {
    public T next();
}