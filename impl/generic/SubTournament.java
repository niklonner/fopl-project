package generic;

import java.util.*;

public abstract class SubTournament<ResultType extends Comparable<? super ResultType>>
    extends Observable implements PlayerReceiver<ResultType>, Iterable<Node<ResultType>> {
    private static int nextId = 0;
    private int id;
    protected String name;
    protected List<Player<ResultType>> players = new ArrayList<>();
    protected List<Node<ResultType>> nodes = new ArrayList<>();
    protected List<Observer> observers = new ArrayList<>();

    // only for use by subclasses
    protected SubTournament() {
        //      this("subtournament " + nextId);
    }

    //
    // protected SubTournament(String name) {
    //  id = nextId++;
    //  this.name = name;
    //  players = new ArrayList<>();
    //  nodes = new ArrayList<>();
    // }

    protected SubTournament(Builder<?,ResultType> builder) {
        players = builder.subTournament.players;
        observers = builder.subTournament.observers;
        for (Observer o : observers) {
            addObserver(o);
        }
    }

    public static abstract class Builder<T extends Builder<T,ResultType>, ResultType extends Comparable<? super ResultType>> {
        protected SubTournament<ResultType> subTournament;

        public Builder() {
            subTournament = createSubTournament();
        }

        protected abstract SubTournament<ResultType> createSubTournament();
        protected abstract T me();

        public T setPlayers(List<Player<ResultType>> players) {
            if (players != null) {
                subTournament.players = new ArrayList<>(players);
            }
            return me();
        }
        
        public T setObservers(List<Observer> observers) {
            if (observers != null) {
                subTournament.observers = new ArrayList<>(observers);
            }
            return me();
        }

    }
    
    public Iterator<Node<ResultType>> iterator() {
        return nodes.iterator(); // TODO: implement topological sort
    }

    public abstract void startBuild();

}
