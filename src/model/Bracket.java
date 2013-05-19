package model;

import java.util.*;
import util.*;
import sets.*;
import parse.*;

import java.awt.*;
import java.awt.geom.*;

import org.apache.batik.swing.*;
import org.apache.batik.svggen.*;


// KEYWORDS
// sendto
// groupby
// playuntil
// advance
// sendlosersto
// sendtopernode

public class Bracket<ResultType extends Comparable<? super ResultType>> extends SubTournament<ResultType> {
    private static Random rnd = new Random();

    private int groupBy;
    private int advancing;
    private int playUntil;
    private java.util.List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> receivers = new ArrayList<>();
    // perNodeReceivers allows sendto statements per node. This is used for sending losers of each node, for example.
    private java.util.List<Pair<PlayerReceiver<ResultType>, SetModifier<Player<ResultType>>>> perNodeReceivers = new ArrayList<>();

    private java.util.List<BracketNodeLayer> nodeLayers = new ArrayList<>();
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

        public SubTournament<ResultType> getSubTournament() {
            return new Bracket<>(this);
        }

        public Builder<ResultType> sendTo(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
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

        public Builder<ResultType> playUntil(int playUntil) {
            subTournament.playUntil = playUntil;
            return this;
        }

        public Builder<ResultType> advance(int advancing) {
            subTournament.advancing = advancing;
            return this;
        }

        public Builder<ResultType> sendLosersTo(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
            return sendToPerNode(receiver,new BottomMod<Player<ResultType>>(subTournament.groupBy-subTournament.advancing, mod));
        }

        public Builder<ResultType> sendToPerNode(PlayerReceiver<ResultType> receiver, SetModifier<Player<ResultType>> mod) {
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
        //dummyRun();
    }

    protected void dummyRun() {
        // add players
        int level = 0;
        BracketNodeLayer prevLayer = null;
        for (BracketNodeLayer bnl : nodeLayers) {
            Iterator<BracketNode<ResultType>> it = bnl.nodes.iterator();
            BracketNode<ResultType> node = null;
            //            while (it.hasNext() && (node = it.next()).isFull()) {
            //            }
            // advance past occupied nodes
            int playerCounter = prevLayer == null ? 0 :
                prevLayer.nodes.size()*advancing;
            int occupiedNodes = prevLayer == null ? 0 :
                (int)Math.ceil((double)playerCounter/groupBy);
            for (int i=0;i<occupiedNodes;i++) {
                node = it.next();
                System.out.println(node.getId());
            }
            for (Player<ResultType> p : players) {
                if ((!p.attributeIsSet("level") && level==0) || (p.attributeIsSet("level") && (int)p.get("level") == level)) {
                    System.out.println("add player " + p.getId());
                    if (playerCounter % groupBy == 0) {
                        node = it.next();
                    }
                    node.acceptPlayer(p);
                    playerCounter++;
                }
            }
            level++;
            prevLayer = bnl;
        }
        // now randomize results
        // hard-coded to use Integer result type
        for (BracketNodeLayer bnl : nodeLayers) {
            for (BracketNode<ResultType> n : bnl.nodes) {
                for (Player<ResultType> p : n.getPlayers()) {
                    //                    System.out.println(n.getId());
                    //                    System.out.println(n.getPlayers());
                    ((BracketNode<Integer>)n).addResult(p.getId(),rnd.nextInt(100));
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

    private int xSpacing = 60;
    public void  draw(SVGGraphics2D g) {
        Map<Node, Point> positions = new HashMap<>();

        Shape box = new Rectangle2D.Double(0,0,50,50);
        Shape circle = new Ellipse2D.Double(0, 0, 50, 50);

        BracketNodeLayer firstLayer = nodeLayers.get(0);

        for(int i = 0; i < firstLayer.nodes.size(); i++) {
            Node node = firstLayer.nodes.get(i);

            // Set positions
            for(PlayerReceiver<?> receiver : node.getReceivers()) {
                //if(receiver instanceof Node) {
                    //positions.put(((Node) receiver), i * xSpacing);
                //}
            }

            // Draw node
            g.setPaint(Color.white);
            g.fill(box);

            // Draw players
            Set<Player<?>> players = node.getPlayers();
            int halfsize = players.size() * xSpacing;
            g.translate(-(halfsize / 2), 60);
            for(int j = 0; j < players.size(); j++) {
                g.translate(j*xSpacing, 0);
                g.setPaint(Color.red);
                g.fill(circle);
            }
            g.translate(halfsize / 2, -60);
        }

        //for(int l = 1; l < nodeLayers.size(); l++) {
            //BracketNodeLayer layer = nodeLayers.get(l);

            //for(int n = 0; n < layer.size(); n++) {
            //
        //}
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
        java.util.List<BracketNode<ResultType>> nodes = new ArrayList<>();
        SetModifier<Player<ResultType>> advancingMod;

        BracketNodeLayer(int numNodes) {
            advancingMod = new TopMod<Player<ResultType>>(advancing);
            for (int i=0;i<numNodes;i++) {
                BracketNode.Builder<ResultType> builder = new BracketNode.Builder<>();
                builder.setNumPlayers(groupBy);
                builder.setObservers(observers);
                // set comparator
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
            for (int i=0;i<nodes.size();i+=treeWidth) {
                BracketNode<ResultType> nextNode = next.nodes.get(i/treeWidth);
                for (int j=0;j<treeWidth && i+j<nodes.size();j++) {
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
