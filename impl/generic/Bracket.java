package generic;

import java.util.*;
import util.*;

// KEYWORDS
// sendto
// groupby
// playuntil
// advance
// sendlosersto
// sendtopernode

public class Bracket<ResultType extends Comparable<? super ResultType>> extends SubTournament<ResultType> {
    private int groupBy;
    private int advancing;
    private int playUntil;
    private List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> receivers = new ArrayList<>();
    // perNodeReceivers allows sendto statements per node. This is used for sending losers of each node, for example.
    private List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> perNodeReceivers = new ArrayList<>();

    private List<BracketNodeLayer> nodeLayers = new ArrayList<>();
    private FinalLayerNode finalLayer;
    private boolean built = false;
    
    // only for use by Builder
    private Bracket() {
        super();
    }

    public Bracket(Builder<ResultType> builder) {
        super(builder);
        playUntil = builder.subTournament.playUntil;
        groupBy = builder.subTournament.groupBy;
        advancing = builder.subTournament.advancing;
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

        public Builder<ResultType> sendTo(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
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

        public Builder<ResultType> sendlosersto(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
            return sendtopernode(receiver,new BottomMod<Player<ResultType>>(subTournament.groupBy-subTournament.advancing, mod));
        }
        
        public Builder<ResultType> sendtopernode(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
            subTournament.perNodeReceivers.add(new Pair<>(receiver, mod));
            return this;
        }
        
    }    

    // should perhaps be named addPlayer
    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
    }

    // don't call until all (dummy) players have been added
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
        finalLayer = new FinalLayerNode();
        for (Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>> p : receivers) {
            finalLayer.addReceiver(p.fst, p.snd);
        }
        previousLayer.connectWith(finalLayer);
        built = true;
        System.out.println(nodes.size() + " nodes");
    }

    private int numPlayersWithLevel(int level) {
        int ret = 0;
        for (Player p : players) {
            if ((!p.attributeIsSet("level") && level==0) || (p.attributeIsSet("level") && (int)p.getAttribute("level")==level)) {
                ret++;
            }
        }
        return ret;
    }

    // node for remaining players ("winners") when bracket is done
    // this enables ranking of all "winners", if more than one
    private class FinalLayerNode extends Node<ResultType> {

        public void acceptPlayer(Player<ResultType> p) {
            super.acceptPlayer(p);
            if (players.size() == playUntil) {
                sendPlayersOff();
            }
        }
        
        public void addResult(Integer i, ResultType r) {
            // should not be called
        }
    }
    
    private class BracketNodeLayer {
        List<BracketNode<ResultType>> nodes = new ArrayList<>();
        SetModifier<Player<ResultType>> advancingMod;
        
        BracketNodeLayer(int numNodes) {
            advancingMod = new TopMod<Player<ResultType>>(advancing);
            for (int i=0;i<numNodes;i++) {
                BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
                builder.setNumPlayers(groupBy);
                builder.setObservers(observers);
                // set comparator
                // set observers
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
                    for (Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>> p : perNodeReceivers) {
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
