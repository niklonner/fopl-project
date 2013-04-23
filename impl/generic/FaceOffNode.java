package generic;

import java.util.*;

/*
  Simple example class for a specific Node type. Will probably not be used in final version.
  Represents a faceoff consisting of exactly two players. Highest score wins and is sent off to the next node.
 */

public class FaceOffNode extends Node {

    public void addResult(String playerName, int result) {
	Player p = players.get(playerName);
	p.setResult(result);
	if (allResultsSet()) {
	    Iterator<Player> it = players.values().iterator();
	    Player winner = it.next();
	    Player p2 = it.next();
	    if (winner.getResult() < p2.getResult()) {
		winner = p2;
	    }
	    nodes.values().iterator().next().addPlayer(winner);
	}
    }

    private boolean allResultsSet() {
	for (Player p : players.values()) {
	    if (!p.resultIsSet()) {
		return false;
	    }
	}
	return true;
    }
}