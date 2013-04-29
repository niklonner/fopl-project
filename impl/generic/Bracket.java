package generic;

import java.util.*;
import util.*;

public class Bracket<ResultType extends Comparable<? super ResultType>> extends SubTournament<ResultType> {
    private int groupBy;
    private int advancing;
    private int numPlayers;

    // preconditions: numPlayers is 2^n for some positive integer n
    //                numPlayers % groupBy == 0
    //                0 < advancing <= groupBy
    public Bracket(int groupBy, int advancing, int numPlayers) {
	this.groupBy = groupBy;
	this.advancing = advancing;
	this.numPlayers = numPlayers;
	while(numPlayers > 0) { // create first-level nodes
	    BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
	    builder.setNumPlayers(groupBy);
	    Node<ResultType> n = new BracketNode<ResultType>(builder);
	    nodes.add(n);
	    numPlayers -= groupBy;
	}
	int remainingPlayers = numPlayers;
	while(remainingPlayers > 1) { // build and link levels
	    remainingPlayers = buildNewLevel(remainingPlayers);
	}
    }

    // remaining is the number of players able to enter the (current) last node level
    // returns number of players able to enter the (current) last node level
    private int buildNewLevel(int remaining) {
	int tmpRemaining = numPlayers;
	//	int levels = 0;
	boolean iteratorAdvanced = false;
	Iterator<Node<ResultType>> it = nodes.iterator(); // this will point to the last node of
	                                                  // the second last level of nodes
	while (tmpRemaining > remaining) {
	    if (iteratorAdvanced) {
		it.next();
	    } else {
		iteratorAdvanced = true;
	    }
	    int numNodesOnLevel = tmpRemaining/groupBy;
	    for (int i=0;i<numNodesOnLevel-1;i++) {
		it.next();         // advance to last node of this level
	    }
	    tmpRemaining -= (advancing-groupBy)*tmpRemaining/groupBy;
	    //	    levels++;
	}
	// ok, lets build a new level:
	int nodesOnLevel = (remaining-(advancing-groupBy)*remaining/groupBy)/groupBy;
	for (int i=0;i<nodesOnLevel/2;i++) {
	    // create new node
	    BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
	    builder.setNumPlayers(groupBy);
	    Node<ResultType> newNode = new BracketNode<ResultType>(builder);
	    nodes.add(newNode);
	    // link nodes
	    SetModifier sm = null; // new Top(advancing); too tired to think about this
	    Node<ResultType> n1 = it.next();
	    n1.addReceiver(newNode, sm);
	    Node<ResultType> n2 = it.next();
	    n2.addReceiver(newNode, sm);
	}
	return remaining-(advancing-groupBy)*remaining/groupBy;
    }
    
    public void acceptPlayer(Player<ResultType> p) {
	players.add(p);
    }

    
}