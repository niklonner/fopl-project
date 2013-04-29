package generic;

import java.util.*;

/*
  Represents a faceoff consisting of exactly two players. Highest score wins and is sent off to the next node.
 */

public class BracketNode<ResultType extends Comparable<? super ResultType>> extends Node<ResultType> {
    private int numPlayers;

    public BracketNode(Builder<ResultType> builder) {
	super(builder);
	this.numPlayers = numPlayers;
    }

    // Even if this Builder subclass does not introduce anything new
    // it is still here for consistency.
    public static class Builder<ResultType extends Comparable<? super ResultType>>
	extends Node.Builder<ResultType> {
	private int numPlayers;

	public Builder setNumPlayers(int numPlayers) {
	    this.numPlayers = numPlayers;
	    return this;
	}
    }
    
    public void addResult(Integer playerId, ResultType result) {
	
    }

    private boolean allResultsSet() {
	return false;
    }
}