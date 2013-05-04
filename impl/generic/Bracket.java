package generic;

import java.util.*;
import util.*;

public class Bracket<ResultType extends Comparable<? super ResultType>> extends SubTournament<ResultType> {
    private int groupBy;
    private int advancing;
    private int playUntil;
    private int numPlayers; //??
    private boolean useLevels;
    private List<Pair<PlayerReceiver, SetModifier<Player<?>>>> receivers = new ArrayList<>();
    private List<Pair<PlayerReceiver, SetModifier<Player<?>>>> perNodeReceivers = new ArrayList<>();

    private List<BracketNodeLayer> nodeLayers = new ArrayList<>();
    private FinalLayerNode finalLayer;
    private boolean built = false;
    
    // keywords:
    // sendto
    // groupby
    // playuntil
    // advance
    // sendlosersto (eller speciellt argument till sendto?)
    // sendtopernode
    
    // only for use by Builder
    private Bracket() {
        super();
    }

    // // preconditions: numPlayers is 2^n for some positive integer n
    // //                numPlayers % groupBy == 0
    // //                0 < advancing <= groupBy
    // public Bracket(int groupBy, int advancing, int numPlayers) {
    //     this.groupBy = groupBy;
    //     this.advancing = advancing;
    //     this.numPlayers = numPlayers;
    //     while(numPlayers > 0) { // create first-level nodes
    //         BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
    //         builder.setNumPlayers(groupBy);
    //         Node<ResultType> n = new BracketNode<ResultType>(builder);
    //         nodes.add(n);
    //         numPlayers -= groupBy;
    //     }
    //     int remainingPlayers = numPlayers;
    //     while(remainingPlayers > 1) { // build and link levels
    //         remainingPlayers = buildNewLevel(remainingPlayers);
    //     }
    // }

    public Bracket(Builder<ResultType> builder) {
        super(builder);
        groupBy = builder.subTournament.groupBy;
        advancing = builder.subTournament.advancing;
        useLevels = builder.subTournament.useLevels;
        receivers = builder.subTournament.receivers;
        perNodeReceivers = builder.subTournament.perNodeReceivers;
    }

    public static class Builder<ResultType extends Comparable<? super ResultType>>
        extends SubTournament.Builder<Bracket.Builder<ResultType>, ResultType> {
        protected Bracket<ResultType> subTournament;

        public Builder() {
            super();
        }

        protected SubTournament<ResultType> createSubTournament() {
            subTournament = new Bracket<>();
            return subTournament;
        }

        protected Builder<ResultType> me() {
            return this;
        }

        public Builder<ResultType> sendto(PlayerReceiver receiver, SetModifier<Player<?>> mod) {
            subTournament.receivers.add(new Pair<>(receiver,mod));
            return this;
        }

        public Builder<ResultType> groupby(int groupBy) {
            subTournament.groupBy = groupBy;
            return this;
        }
        
        public Builder<ResultType> playuntil(int playUntil) {
            subTournament.playUntil = playUntil;
            return this;
        }

        public Builder<ResultType> advance(int advancing) {
            subTournament.advancing = advancing;
            return this;
        }

        public Builder<ResultType> sendlosersTo(PlayerReceiver receiver, SetModifier<Player<?>> mod) {
            return sendtopernode(receiver,new BottomMod<Player<?>>(subTournament.groupBy-subTournament.advancing, mod));
        }
        
        public Builder<ResultType> sendtopernode(PlayerReceiver receiver, SetModifier<Player<?>> mod) {
            subTournament.perNodeReceivers.add(new Pair<>(receiver, mod));
            return this;
        }
        
    }
    
    // // remaining is the number of players able to enter the (current) last node level
    // // returns number of players able to enter the (current) last node level
    // private int buildNewLevel(int remaining) {
    //     int tmpRemaining = numPlayers;
    //     //      int levels = 0;
    //     boolean iteratorAdvanced = false;
    //     Iterator<Node<ResultType>> it = nodes.iterator(); // this will point to the last node of
    //                                                       // the second last level of nodes
    //     while (tmpRemaining > remaining) {
    //         if (iteratorAdvanced) {
    //             it.next();
    //         } else {
    //             iteratorAdvanced = true;
    //         }
    //         int numNodesOnLevel = tmpRemaining/groupBy;
    //         for (int i=0;i<numNodesOnLevel-1;i++) {
    //             it.next();         // advance to last node of this level
    //         }
    //         tmpRemaining -= (advancing-groupBy)*tmpRemaining/groupBy;
    //         //      levels++;
    //     }
    //     // ok, lets build a new level:
    //     int nodesOnLevel = (remaining-(advancing-groupBy)*remaining/groupBy)/groupBy;
    //     for (int i=0;i<nodesOnLevel/2;i++) {
    //         // create new node
    //         BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
    //         builder.setNumPlayers(groupBy);
    //         Node<ResultType> newNode = new BracketNode<ResultType>(builder);
    //         nodes.add(newNode);
    //         // link nodes
    //         SetModifier sm = null; // new Top(advancing); too tired to think about this
    //         Node<ResultType> n1 = it.next();
    //         n1.addReceiver(newNode, sm);
    //         Node<ResultType> n2 = it.next();
    //         n2.addReceiver(newNode, sm);
    //     }
    //     return remaining-(advancing-groupBy)*remaining/groupBy;
    // }
    
    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
    }

    public void startBuild() {
        if (built) {
            return;
        }
        // build first level
        int numPlayers = numPlayersWithLevel(0);
        int numNodes = numPlayers/groupBy;
        BracketNodeLayer previousLayer = new BracketNodeLayer(numNodes);
        nodeLayers.add(previousLayer);
        int remaining = numNodes*advancing;
        // build remaining levels
        int level = 1;
        while (remaining > playUntil) {
            numPlayers = numPlayersWithLevel(level) + remaining;
            numNodes = numPlayers/groupBy;
            BracketNodeLayer layer = new BracketNodeLayer(numNodes);
            previousLayer.connectWith(layer);
            nodeLayers.add(layer);
            previousLayer = layer;
            remaining = numNodes*advancing;
            level++;
        }
        // build final level (which is not really a level in the bracket,
        // it just gathers remaining players to allow ranking of them)
        finalLayer = new FinalLayerNode();
        previousLayer.connectWith(finalLayer);
        built = true;
    }

    private int numPlayersWithLevel(int level) {
        int ret = 0;
        for (Player p : players) {
            if ((int)p.getAttribute("level")==level) {
                ret++;
            }
        }
        return ret;
    }

    private class FinalLayerNode extends Node<ResultType> {
        public void addResult(Integer i, ResultType r) {
            // should not be called
        }
    }
    
    private class BracketNodeLayer {
        List<BracketNode<ResultType>> nodes = new ArrayList<>();
        SetModifier<Player<?>> advancingMod;
        
        BracketNodeLayer(int numNodes) {
            advancingMod = new TopMod<Player<?>>(advancing);
            for (int i=0;i<numNodes;i++) {
                BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
                builder.setNumPlayers(groupBy);
                BracketNode<ResultType> newNode = new BracketNode<ResultType>(builder);
                nodes.add(newNode);
                Bracket.this.nodes.add(newNode);
            }
        }

        int size() {
            return nodes.size();
        }

        void connectWith(BracketNodeLayer next) {
            int treeWidth = groupBy/advancing; // number of nodes in previous level to connect to each node in new level
            for (int i=0;i<=nodes.size()-treeWidth;i+=treeWidth) {
                BracketNode<ResultType> nextNode = next.nodes.get(i/treeWidth);
                for (int j=0;j<treeWidth;j++) {
                    BracketNode<ResultType> n1 = nodes.get(i+j);
                    n1.addReceiver(nextNode,advancingMod);
                    for (Pair<PlayerReceiver, SetModifier<Player<?>>> p : perNodeReceivers) {
                        n1.addReceiver(p.fst,p.snd);
                    }
                }
            }
        }

        void connectWith(FinalLayerNode fln) {
            for (BracketNode<ResultType> bn : nodes) {
                bn.addReceiver(fln, advancingMod);
            }
        }
    }
}