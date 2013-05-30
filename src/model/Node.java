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

    public abstract void beforeSendOffHook(Player<ResultType> p);
    public abstract void afterSendOffHook(Player<ResultType> p);
    /* "driver" method. Should rank players and, when appropriate, send them to next node */
    public abstract void addResult(Integer playerId, ResultType result);

    protected Node() {
        id = nextId;
        name = "node " + (nextId++);
    }

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

    public void addReceiver(PlayerReceiver<ResultType> p, SetModifier<Player<ResultType>> s) {
        toReceivers.add(new Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>(p,s));
        stdNotify();
    }

    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
        stdNotify();
    }

    protected void sendPlayersOff() {
        for (Player<ResultType> p : players) {
            beforeSendOffHook(p);
        }
        List<Pair<Player<ResultType>, PlayerReceiver<ResultType>>> notification = new ArrayList<>();
        for (Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>> pair : toReceivers) {
            for (Player<ResultType> p : pair.snd.apply(players,comp)) {
                notification.add(new Pair<>(p.clone(),pair.fst));
                afterSendOffHook(p);
                pair.fst.acceptPlayer(p);
            }
        }
        for (Player<ResultType> p : players) {
            boolean inList = false;
            for (Pair<Player<ResultType>,?> pair : notification) {
                if (p.equals(pair.fst)) {
                    inList = true;
                    break;
                }
            }
            if (!inList) {
                notification.add(new Pair<Player<ResultType>,PlayerReceiver<ResultType>>(p,null));
            }
        }
        setChanged();
        notifyObservers(new Pair<>(model.EventType.SENDOFF,notification));
    }

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
        notifyObservers(new Pair<>(model.EventType.UPDATE,null));
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
        stdNotify();
    }

    // debug.
    private void printMods() {
        System.out.println(toReceivers);
    }
}
