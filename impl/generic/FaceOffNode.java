package generic;

import java.util.*;

/*
  Represents a faceoff consisting of exactly two players. Highest score wins and is sent off to the next node.
 */

public class FaceOffNode<PlayerType extends Player<?>> extends Node<PlayerType> {

    public FaceOffNode(Builder<PlayerType> builder) {
	super(builder);
    }

    // Even if this Builder subclass does not introduce anything new
    // it is still here for consistency.
    public static class Builder<PlayerType extends Player<?>> extends Node.Builder<PlayerType> {
	
    }
    
    public void addResult(String playerName, int result) {
	// Player p = players.get(playerName);
	// p.setResult(result);
	// if (allResultsSet()) {
	//     Iterator<Player> it = players.values().iterator();
	//     Player winner = it.next();
	//     Player p2 = it.next();
	//     if (winner.getResult() < p2.getResult()) {
	// 	winner = p2;
	//     }
	//     nodes.values().iterator().next().addPlayer(winner);
	// }
    }

    private boolean allResultsSet() {
	// for (Player p : players.values()) {
	//     if (!p.resultIsSet()) {
	// 	return false;
	//     }
	// }
	// return true;
	return false;
    }
}