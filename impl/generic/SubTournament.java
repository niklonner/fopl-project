package generic;

import java.util.*;

public abstract class SubTournament<ResultType extends Comparable<? super ResultType>>
    extends Observable implements PlayerReceiver<ResultType>, Iterable<Node<ResultType>> {
    private static int nextId = 0;
    private int id;
    protected String name;
    protected List<Player<ResultType>> players;
    protected List<Node<ResultType>> nodes;

    protected SubTournament() {
	this("subtournament " + nextId);
    }

    protected SubTournament(String name) {
	id = nextId++;
	this.name = name;
	players = new ArrayList<>();
	nodes = new ArrayList<>();
    }

    public Iterator<Node<ResultType>> iterator() {
	return nodes.iterator(); // TODO: implement topological sort
    }

}