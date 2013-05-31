package model;

import java.util.*;

/* BracketNode
 * Used by Bracket-style subtournaments. Keeps track of how many players that should be
 * in the node (though it is not enforced) and how many that advances. Could probably
 * be used by subtournaments other than Bracket-styled such.
 */

public class BracketNode<ResultType> extends Node<ResultType> {
    private int numPlayers;
    private int advancing;

    // for use by builder only
    private BracketNode() {
        super();
    }

    public BracketNode(Builder<ResultType> builder) {
        super(builder);
        this.numPlayers = builder.node.numPlayers;
        this.advancing = builder.node.advancing;
    }

    // TODO: Add hook to modifiy players before sending them off

    public static class Builder<ResultType>
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

        public Builder<ResultType> setAdvancing(int advancing) {
            this.node.advancing = advancing;
            return this;
        }

    }

    @Override
    public void acceptPlayer(Player<ResultType> p) {
        super.acceptPlayer(p);
        p.resetResult();
    }

    public void addResult(Integer playerId, ResultType result) {
        Iterator<Player<ResultType>> it = players.iterator();
        Player<ResultType> p = null;
        while (it.hasNext() && (p=it.next()).getId()!=playerId) {
        }
        if (p.getId() == playerId) {
            it.remove(); // needed to update ordering of set
            p.setResult(result);
            players.add(p);
        }
        if (allResultsSet()) {
            sendPlayersOff();
        }
    }

    public void beforeSendOffHook(Player<ResultType> player) {
        if (rank(player) > advancing) {
            if (player.attributeIsSet("losses")) {
                player.set("losses",((int)player.get("losses"))+1);
            } else {
                player.set("losses",1);
            }
        }
    }

    public void afterSendOffHook(Player<ResultType> player) {
        if (rank(player) <= advancing) {
            if (player.attributeIsSet("level")) {
                player.set("level",((int)player.get("level"))+1);
            } else {
                player.set("level",1);
            }
        } 
    }

    private boolean allResultsSet() {
        for (Player<?> p : players) {
            if (!p.resultIsSet()) {
                return false;
            }
        }
        return true;
    }
    
    public void addObserver(Observer o) {
        super.addObserver(o);
        setChanged();
        notifyObservers(getId());
    }

    public boolean isFull() {
        return players.size() == numPlayers;
    }
}
