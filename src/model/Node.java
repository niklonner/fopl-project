package model;

import java.util.*;
import util.*;
import sets.*;
import parse.*;

/*
  Class for nodes in tournament graph.
  abstract method addResult is responsible for ranking players and sending them to next node or subtournament in the competition.
  End applications should observe Nodes objects to respond to changes.
*/

public abstract class Node<ResultType> extends Observable
    implements PlayerReceiver<ResultType> {
    private static int nextId = 0;
    private int id;
    protected String name;
    protected SortedSet<Player<ResultType>> players = new TreeSet<>();
    protected List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> toReceivers = new ArrayList<>();
    protected Comparator<Player<ResultType>> comp;

    // only for use by subclasses
    protected Node() {
        id = nextId;
        name = "node " + (nextId++);
    }

    // // maybe remove and only allow construction by Builder
    // protected Node(String name) {
    //     //   this(name, null);
    // }

    // // maybe remove and only allow construction by Builder
    // protected Node(String name,
    //         Comparator<Player<ResultType>> comp,
    //         List<Pair<PlayerReceiver, SetModifier>> toReceivers,
    //         SortedSet<Player<ResultType>> players,
    //         List<Observer> observers) {
    //     this.name = name;
    //     players = new TreeSet<>(comp);
    //     this.toReceivers = new LinkedList<>(toReceivers);
    // }

    protected Node(Builder<?,ResultType> builder) {
        id = builder.node.id;
        name = builder.node.name;
        toReceivers = builder.node.toReceivers;
        comp = builder.node.comp;
        if (comp != null) {
            players = new TreeSet<>(comp);
            players.addAll(builder.node.players);
        } else{
            players = builder.node.players;
        }
        for (Observer o : builder.observers) {
            addObserver(o);
        }
    }

    public static abstract class Builder<T extends Builder<T, ResultType>, ResultType> {
        protected Node<ResultType> node;
        protected List<Observer> observers;

        public Builder() {
            node = createNode();
        }

        protected abstract Node<ResultType> createNode();
        protected abstract T me();

        public T name(String name) {
            node.name = name;
            return me();
        }

        public T setPlayers(SortedSet<Player<ResultType>> players) {
            if (players != null) {
                node.players = new TreeSet<>(players);
            }
            return me();
        }

        public T setToReceivers(List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> toReceivers) {
            if (toReceivers != null) {
                node.toReceivers = new LinkedList<>(toReceivers);
            }
            return me();
        }

        public T setComparator(Comparator<Player<ResultType>> comp) {
            node.comp = comp;
            return me();
        }

        public T setObservers(List<Observer> observers) {
            if (observers != null) {
                this.observers = new LinkedList<>(observers);
            }
            return me();
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /* "driver" method. Should rank players and, when appropriate, send them to next node */
    public abstract void addResult(Integer playerId, ResultType result);

    public void addReceiver(PlayerReceiver<ResultType> p, SetModifier<Player<ResultType>> s) {
        toReceivers.add(new Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>(p,s));
    }

    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
        stdNotify();
    }

    protected void sendPlayersOff() {
        System.out.printf("node %d sending players off\n",getId());
        for (Player<ResultType> p : players) {
            System.out.println("\t" + p);
            beforeSendOffHook(p);
        }
        for (Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>> pair : toReceivers) {
            for (Player<ResultType> p : pair.snd.apply(players,comp)) {
                if (pair.fst instanceof Node) {
                    System.out.printf("sending player %d to node %d\n", p.getId(), ((Node<ResultType>)pair.fst).getId());
                } else if (pair.fst instanceof SubTournament) {
                    System.out.printf("sending player %d to subtournament %d\n", p.getId(), ((SubTournament<ResultType>)pair.fst).getId());
                }
                afterSendOffHook(p);
                pair.fst.acceptPlayer(p);
            }
        }
        System.out.println();
    }

    public abstract void beforeSendOffHook(Player<ResultType> p);
    public abstract void afterSendOffHook(Player<ResultType> p);

    public int rank(Player<ResultType> player) {
        int i=0;
        for (Player<ResultType> p : players) {
            ++i;
            if (p.equals(player)) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        return "node " + getId();
    }

    private void stdNotify() {
        setChanged();
        notifyObservers();
    }

    public Set<Player<ResultType>> getPlayers() {
        return new TreeSet(players);
    }

    public List<PlayerReceiver<ResultType>> getReceivers() {
        List<PlayerReceiver<ResultType>> list = new ArrayList<>();
        for (Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>> pair : toReceivers) {
                list.add(pair.fst);
        }
        return list;
    }

    public void clearPlayers() {
        players.clear();
    }

    // debug
    private void printMods() {
        System.out.println(toReceivers);
    }
}
