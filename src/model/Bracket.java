package model;

import java.util.*;
import util.*;
import sets.*;
import parse.*;

// KEYWORDS
// sendto
// groupby
// playuntil
// advance
// sendlosersto
// sendtopernode

public class Bracket<ResultType> extends SubTournament<ResultType> {
    private int groupBy;
    private int advancing;
    private int playUntil;
    private boolean resetLevels;
    private List<Pair<String, SetModifier<Player<ResultType>>>> receivers = new ArrayList<>();
    // perNodeReceivers allows sendto statements per node. This is used for sending losers of each node, for example.
    private List<Pair<String, SetModifier<Player<ResultType>>>> perNodeReceivers = new ArrayList<>();

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
        resetLevels = builder.subTournament.resetLevels;
    }

    public static class Builder<ResultType>
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

        public Bracket<ResultType> getSubTournament() {
            return new Bracket<>(this);
        }

        public Builder<ResultType> sendTo(String receiver, SetModifier<Player<ResultType>> mod) {
            subTournament.receivers.add(new Pair<>(receiver,mod));
            return this;
        }

        public Builder<ResultType> groupBy(int groupBy) {
            subTournament.groupBy = groupBy;
            return this;
        }

        public Builder<ResultType> groupBy(double groupBy) {
            subTournament.groupBy = (int)groupBy;
            return this;
        }

        public Builder<ResultType> groupBy(String keyword) {
            if (keyword.equals("all")) {
                subTournament.groupBy = Integer.MAX_VALUE;
            } else {
                throw new UnsupportedOperationException("expected keyword, got " + keyword);
            }
            return this;
        }

        
        public Builder<ResultType> playUntil(int playUntil) {
            subTournament.playUntil = playUntil;
            return this;
        }

        public Builder<ResultType> playUntil(String keyword) {
            if (keyword.equals("all")) {
                subTournament.playUntil = Integer.MAX_VALUE;
            } else {
                throw new UnsupportedOperationException("expected keyword, got " + keyword);
            }
            return this;
        }

        public Builder<ResultType> advance(int advancing) {
            subTournament.advancing = advancing;
            return this;
        }

        public Builder<ResultType> advance(String keyword) {
            if (keyword.equals("all")) {
                subTournament.advancing = Integer.MAX_VALUE;
            } else {
                throw new UnsupportedOperationException("expected keyword, got " + keyword);
            }
            return this;
        }

        
        public Builder<ResultType> sendLosersTo(String receiver) {
            return sendToPerNode(receiver,new BottomMod<Player<ResultType>>(subTournament.groupBy-subTournament.advancing, new IdentityMod<Player<ResultType>>()));
        }
        
        public Builder<ResultType> sendToPerNode(String receiver, SetModifier<Player<ResultType>> mod) {
            subTournament.perNodeReceivers.add(new Pair<>(receiver, mod));
            return this;
        }

        public Builder<ResultType> resetLevels() {
            subTournament.resetLevels = true;
            return this;
        }
        
    }

    // don't call until all (dummy) players have been added
    public void startBuild() {
        if (built) {
            return;
        }
        // build first level
        int numPlayers = numPlayersWithLevel(0);
        /*        for (Player<?> p : players) {
            System.out.println(p.get("level"));
            }*/
        int numNodes = (int)Math.ceil((double)numPlayers/groupBy);
        BracketNodeLayer previousLayer = new BracketNodeLayer(numNodes,numPlayers);
        nodeLayers.add(previousLayer);
        //        int remaining = previousLa;
        // build remaining levels
        int level = 1;
        numPlayers = numPlayersWithLevel(level) + previousLayer.getAdvancing();
        while (numPlayers > playUntil) {
            numNodes = (int)Math.ceil((double)numPlayers/groupBy);
            //            System.out.println(numPlayers);
            BracketNodeLayer layer = new BracketNodeLayer(numNodes,numPlayers);
            previousLayer.connectWith(layer);
            nodeLayers.add(layer);
            previousLayer = layer;
            //            remaining = numNodes*advancing;
            level++;
            numPlayers = numPlayersWithLevel(level) + previousLayer.getAdvancing();
        }
        finalLayer = new FinalLayerNode();
        for (Pair<String, SetModifier<Player<ResultType>>> p : receivers) {
            finalLayer.addReceiver(tournament.findSubTournament(p.fst), p.snd);
        }
        previousLayer.connectWith(finalLayer);
        built = true;
        dummyRun();
    }

    protected void dummyRun() {
        // add players
        int level = 0;
        BracketNodeLayer prevLayer = null;
        for (BracketNodeLayer bnl : nodeLayers) {
            Iterator<BracketNode<ResultType>> it = bnl.nodes.iterator();
            BracketNode<ResultType> node = null;
            int playerCounter = prevLayer == null ? 0 :
                prevLayer.getAdvancing();
            int occupiedNodes = prevLayer == null ? 0 :
                (int)Math.ceil((double)playerCounter/groupBy);
            // advance past occupied nodes
            for (int i=0;i<occupiedNodes;i++) {
                node = it.next();
            }
            for (Player<ResultType> p : players) {
                if ((!p.attributeIsSet("level") && level==0) || (p.attributeIsSet("level") && (int)p.get("level") == level)) {
                    if (playerCounter % groupBy == 0) {
                        node = it.next();
                    }
                    System.out.println("add player " + p.getId() + " at node " + node.getId()  + " (level " + level + ")");
                    node.acceptPlayer(p);
                    playerCounter++;
                }
            }
            level++;
            prevLayer = bnl;
        }
        // now randomize results
        for (BracketNodeLayer bnl : nodeLayers) {
            for (BracketNode<ResultType> n : bnl.nodes) {
                for (Player<ResultType> p : n.getPlayers()) {
                    //                    System.out.println(n.getId());
                    //                    System.out.println(n.getPlayers());
                    n.addResult(p.getId(),rnd.next());
                }
            }
        }

    }

    private int numPlayersWithLevel(int level) {
        int ret = 0;
        for (Player p : players) {
            if ((!p.attributeIsSet("level") && level==0) || (p.attributeIsSet("level") && (int)p.get("level")==level)) {
                ret++;
            }
        }
        return ret;
    }

    public void receiveHook(Player<ResultType> p) {
        if (resetLevels) {
            p.set("level",0);
        }
    }
    
    // node for remaining players ("winners") when bracket is done
    // this enables ranking of all "winners", if more than one
    private class FinalLayerNode extends Node<ResultType> {

        public void acceptPlayer(Player<ResultType> p) {
            super.acceptPlayer(p);
            if (players.size() == playUntil || Bracket.this.players.size() == players.size()) {
                sendPlayersOff();
            }
        }

        public FinalLayerNode() {
            super();
            if (Bracket.this.comp != null) {
                players = new TreeSet<>(Bracket.this.comp);
                comp = Bracket.this.comp;
            }
        }
        
        public void beforeSendOffHook(Player<ResultType> p) {
            
        }
        
        public void afterSendOffHook(Player<ResultType> p) {
            
        }
        
        public void addResult(Integer i, ResultType r) {
            // should not be called
        }
    }
    
    private class BracketNodeLayer {
        List<BracketNode<ResultType>> nodes = new ArrayList<>();
        SetModifier<Player<ResultType>> advancingMod;
        int advancingPlayers;
        int numPlayers;
        
        BracketNodeLayer(int numNodes, int numPlayers) {
            advancingMod = new TopMod<Player<ResultType>>(advancing);
            int leftOverPlayers = numPlayers%numNodes;
            advancingPlayers = numPlayers/groupBy * advancing + (leftOverPlayers == 0 ? 0 : Math.min(leftOverPlayers,advancing));
            this.numPlayers = numPlayers;
            for (int i=0;i<numNodes;i++) {
                BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
                if (leftOverPlayers != 0 && i==numNodes-1) {
                    builder.setNumPlayers(leftOverPlayers);
                    builder.setAdvancing(Math.min(advancing,leftOverPlayers));
                } else {
                    builder.setNumPlayers(groupBy);
                    builder.setAdvancing(advancing);
                }
                builder.setObservers(observers);
                builder.setComparator(comp);
                BracketNode<ResultType> newNode = new BracketNode<ResultType>(builder);
                nodes.add(newNode);
                Bracket.this.nodes.add(newNode);
            }
        }

        int getAdvancing() {
            return advancingPlayers;
        }

        int size() {
            return nodes.size();
        }

        void connectWith(BracketNodeLayer next) {
            int treeWidth = groupBy/advancing; // number of nodes in previous level to connect to each node in new level
            for (int i=0;i<nodes.size();i+=treeWidth) {
                BracketNode<ResultType> nextNode = next.nodes.get(i/treeWidth);
                for (int j=0;j<treeWidth && i+j<nodes.size();j++) {
                    BracketNode<ResultType> n1 = nodes.get(i+j);
                    n1.addReceiver(nextNode,advancingMod);
                    for (Pair<String, SetModifier<Player<ResultType>>> p : perNodeReceivers) {
                        n1.addReceiver(tournament.findSubTournament(p.fst),p.snd);
                    }
                }
            }
        }

        void connectWith(FinalLayerNode fln) {
            for (BracketNode<ResultType> bn : nodes) {
                bn.addReceiver(fln, advancingMod);
                for (Pair<String, SetModifier<Player<ResultType>>> p : perNodeReceivers) {
                    bn.addReceiver(tournament.findSubTournament(p.fst),p.snd);
                }
            }
        }
    }

    public void addObserver(Observer o) {
        for (Node<ResultType> n : nodes) {
            n.addObserver(o);
        }
    }
}
