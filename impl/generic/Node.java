package generic;

import java.util.*;
import util.Pair;

/*
  Class for nodes in tournament graph.
  abstract method addResult is responsible for ranking players and sending them to next node or subtournament in the competition.
  End applications should observe Nodes objects to respond to changes.
*/

public abstract class Node<ResultType extends Comparable<? super ResultType>> extends Observable
    implements PlayerReceiver<ResultType> {
    private static int nextId = 0;
    private int id;
    protected String name;
    protected SortedSet<Player<ResultType>> players;
    protected List<Pair<PlayerReceiver, SetModifier>> toReceivers;
    protected Comparator<Player<ResultType>> comp;

    // only for use by Builder!
    private Node() {
	id = nextId;
	name = "node " + (nextId++);
	players = new TreeSet<>();
	toReceivers = new LinkedList<>();
    }

    // maybe remove and only allow construction by Builder
    protected Node(String name) {
	//	this(name, null);
    }

    // maybe remove and only allow construction by Builder
    protected Node(String name,
		   Comparator<Player<ResultType>> comp,
		   List<Pair<PlayerReceiver, SetModifier>> toReceivers,
		   SortedSet<Player<ResultType>> players,
		   List<Observer> observers) {
	this.name = name;
	players = new TreeSet<>(comp);
	this.toReceivers = new LinkedList<>(toReceivers);
    }

    protected Node(Builder<ResultType> builder) {
	name = builder.name != null ? builder.name : "node " + nextId++;
	players = builder.players;
	toReceivers = builder.toReceivers;
	comp = builder.comp;
	for (Observer o : builder.observers) {
	    addObserver(o);
	}
    }
    
    public static class Builder<ResultType extends Comparable<? super ResultType>> {
	protected String name;
	protected SortedSet<Player<ResultType>> players = new TreeSet<>();
	protected List<Pair<PlayerReceiver, SetModifier>> toReceivers = new LinkedList<>();
	protected Comparator<Player<ResultType>> comp;
	protected List<Observer> observers = new LinkedList<>();

	public Builder name(String name) {
	    this.name = name;
	    return this;
	}

	public Builder setPlayers(SortedSet<Player<ResultType>> players) {
	    if (players != null) {
		this.players = new TreeSet<>(players);
	    }
	    return this;
	}

	public Builder setToReceivers(List<Pair<PlayerReceiver, SetModifier>> toReceivers) {
	    if (toReceivers != null) {
		this.toReceivers = new LinkedList<>(toReceivers);
	    }
	    return this;
	}

	public Builder setComparator(Comparator<Player<ResultType>> comp) {
	    this.comp = comp;
	    return this;
	}

	public Builder setObservers(List<Observer> observers) {
	    this.observers = new LinkedList<>(observers);
	    return this;
	}
    }
    
    public String getName() {
	return name;
    }

    // public void addResult(String playerName, int result) {
    // 	addResult(players.get(playerName), result);
    // 	stdNotify();
    // }

    /* "driver" method. Should rank players and, when appropriate, send them to next node */
    public abstract void addResult(Integer playerId, ResultType result);
    
    // public void addResult(Player p, int result) {
    // 	p.setResult(result);
    // 	stdNotify();
    // }

    public void addReceiver(PlayerReceiver p, SetModifier s) {
	toReceivers.add(new Pair<PlayerReceiver, SetModifier>(p,s));
    }
    
    public void acceptPlayer(Player<ResultType> p) {
	addPlayer(p);
    }
    
    public void addPlayer(Player<ResultType> p) {
	players.add(p);
	stdNotify();
    }

    private void stdNotify() {
	setChanged();
	notifyObservers();
    }
}