package generic;

import java.util.*;

/*
  
 */

public class BracketNode<ResultType extends Comparable<? super ResultType>> extends Node<ResultType> {
    private int numPlayers;

    // for use by builder only
    private BracketNode() {
        super();
    }
    
    public BracketNode(Builder<ResultType> builder) {
        super(builder);
        this.numPlayers = builder.node.numPlayers;
    }

    // TODO: Add hook to modifiy players before sending them off
    
    public static class Builder<ResultType extends Comparable<? super ResultType>>
        extends Node.Builder<BracketNode.Builder<ResultType>,ResultType> {
        protected BracketNode<ResultType> node;

        protected Node<ResultType> createNode() {
            node = new BracketNode<ResultType>();
            return node;
        }

        protected Builder<ResultType> me() {
            return this;
        }
        
        public Builder<ResultType> setNumPlayers(int numPlayers) {
            this.node.numPlayers = numPlayers;
            return this;
        }
    }
    
    public void addResult(Integer playerId, ResultType result) {
        
    }

    private boolean allResultsSet() {
        return false;
    }
}