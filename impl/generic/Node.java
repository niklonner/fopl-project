package generic;

import java.util.*;

/*
  Class for nodes in tournament graph. Could represent a bracket, a round robin, etc.
  abstract method addResult is responsible for ranking players and sending them to next node in the competition.
  End applications should observe Nodes objects to respond to changes.
 */

public abstract class Node extends Observable {
    private static int nextId = 0;
    private String name;
    protected Map<String,Player> players;
    protected Map<String,Node> nodes;
    
    public Node() {
	name = nextId + "";
	nextId++;
    }

    public Node(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    // public void addResult(String playerName, int result) {
    // 	addResult(players.get(playerName), result);
    // 	stdNotify();
    // }

    /* "driver" method. Should rank players and, when appropriate, send them to next node */
    public abstract void addResult(String playerName, int result);
    
    // public void addResult(Player p, int result) {
    // 	p.setResult(result);
    // 	stdNotify();
    // }

    public void addPlayer(Player p) {
	players.put(p.getName(), p);
	stdNotify();
    }

    private void stdNotify() {
	setChanged();
	notifyObservers();
    }
}