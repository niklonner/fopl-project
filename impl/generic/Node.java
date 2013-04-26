package generic;

import java.util.*;
import util.Pair;

/*
  Class for nodes in tournament graph.
  abstract method addResult is responsible for ranking players and sending them to next node or subtournament in the competition.
  End applications should observe Nodes objects to respond to changes.
*/

public abstract class Node<ResultType extends Comparable<? super ResultType>> extends Observable {
    private static int nextId = 0;
    protected String name;
    protected SortedSet<Player<ResultType>> players;
    protected List<Pair<Node, SetModifier>> toNodes;
    protected List<Pair<SubTournament, SetModifier>> toSubTournaments;
    protected Comparator<Player<ResultType>> comp;

    // only for use by Builder!
    private Node() {
	name = "node " + (nextId++);
	players = new TreeSet<>();
	toNodes = new LinkedList<>();
	toSubTournaments = new LinkedList<>();
    }

    // maybe remove and only allow construction by Builder
    protected Node(String name) {
	//	this(name, null);
    }

    // maybe remove and only allow construction by Builder
    protected Node(String name,
		   Comparator<Player<ResultType>> comp,
		   List<Pair<Node, SetModifier>> toNodes,
		   List<Pair<SubTournament, SetModifier>> toSubTournaments,
		   SortedSet<Player<ResultType>> players,
		   List<Observer> observers) {
	this.name = name;
	players = new TreeSet<>(comp);
	this.toNodes = new LinkedList<>(toNodes);
    }

    protected Node(Builder<ResultType> builder) {
	name = builder.name != null ? builder.name : "node " + nextId++;
	players = builder.players;
	toNodes = builder.toNodes;
	toSubTournaments = builder.toSubTournaments;
	comp = builder.comp;
	for (Observer o : builder.observers) {
	    addObserver(o);
	}
    }
    
    public static class Builder<ResultType extends Comparable<? super ResultType>> {
	protected String name;
	protected SortedSet<Player<ResultType>> players = new TreeSet<>();
	protected List<Pair<Node, SetModifier>> toNodes = new LinkedList<>();
	protected List<Pair<SubTournament, SetModifier>> toSubTournaments = new LinkedList<>();
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

	public Builder setToNodes(List<Pair<Node, SetModifier>> toNodes) {
	    if (toNodes != null) {
		this.toNodes = new LinkedList<>(toNodes);
	    }
	    return this;
	}

	public Builder setToSubTournaments(List<Pair<SubTournament, SetModifier>> toSubTournaments) {
	    if (toSubTournaments != null) {
		this.toSubTournaments = new LinkedList<>(toSubTournaments);
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
    public abstract void addResult(String playerName, ResultType result);
    
    // public void addResult(Player p, int result) {
    // 	p.setResult(result);
    // 	stdNotify();
    // }

    public void addPlayer(Player<ResultType> p) {
	players.add(p);
	stdNotify();
    }

    private void stdNotify() {
	setChanged();
	notifyObservers();
    }
}