package bwlag.model;

import model.*;
import sets.*;
import java.util.*;

import org.apache.batik.svggen.*;

public class OpenPlay<ResultType> extends SubTournament<ResultType> {
    private Bracket<ResultType> bracket;
    private List<Player<ResultType>> tmpReceiveHook;
    private List<Player<ResultType>> tmpAcceptPlayer;

    private OpenPlay() {
        super();
    }
    
    public OpenPlay(Builder<ResultType> builder) {
        super(builder);
        bracket = builder.bb.getSubTournament();
    }

    public static class Builder<ResultType> extends SubTournament.Builder<OpenPlay.Builder<ResultType>, ResultType> {
        protected OpenPlay<ResultType> op;
        protected Bracket.Builder<ResultType> bb;
        
        public OpenPlay<ResultType> getSubTournament() {
            return new OpenPlay<>(this);
        }

        protected Builder<ResultType> me() {
            return this;
        }

        protected OpenPlay<ResultType> createSubTournament() {
            op = new OpenPlay();
            bb = new Bracket.Builder<>();
            bb.groupBy("all");
            bb.advance("all");
            bb.playUntil("all");
            return op;
        }

        public Builder<ResultType> playerSource(String source) {
            //            super.playerSource(source);
            bb.playerSource(source);
            return this;
        }

        public Builder<ResultType> setPlayers(SortedSet<Player<ResultType>> players) {
            bb.setPlayers(players);
            return me();
        }

        public Builder<ResultType> setObservers(List<Observer> observers) {
            bb.setObservers(observers);
            return me();
        }

        public Builder<ResultType> setPrettyPrinter(PrettyPrinterScore<ResultType> pps) {
            bb.setPrettyPrinter(pps);
            return me();
        }
        
        public Builder<ResultType> sendTo(String dest, SetModifier<Player<ResultType>> mod) {
            bb.sendTo(dest, mod);
            return this;
        }

        public Builder<ResultType> setComparator(Comparator<Player<ResultType>> comp) {
            super.setComparator(comp);
            bb.setComparator(comp);
            return this;
        }


        public Builder<ResultType> setRandomGenerator(RandomGenerator<ResultType> rnd) {
            super.setRandomGenerator(rnd);
            bb.setRandomGenerator(rnd);
            return this;
        }

        public Builder<ResultType> setTournament(Tournament tournament) {
            bb.setTournament(tournament);
            return this;
        }

        public Builder<ResultType> playUntil(int playUntil) {
            bb.playUntil(playUntil);
            return this;
        }
        
    }

    public void receiveHook(Player<ResultType> p) {
        if (bracket != null) {
            bracket.receiveHook(p);
        } else {
            if (tmpReceiveHook == null) {
                tmpReceiveHook = new LinkedList<>();
            }
            tmpReceiveHook.add(p);
        }
    }

    protected void dummyRun() {
        //bracket.dummyRun();
    }

    public void startBuild() {
        bracket.startBuild();
    }

    public SortedSet<Player<ResultType>> getPlayers() {
        return bracket.getPlayers();
    }
    
    public void acceptPlayer(Player<ResultType> p) {
        super.acceptPlayer(p);
        if (bracket != null) {
            bracket.acceptPlayer(p);
        } else {
            if (tmpAcceptPlayer == null) {
                tmpAcceptPlayer = new LinkedList<>();
            }
            tmpAcceptPlayer.add(p);
        }
    }

    public Iterator<Node<ResultType>> iterator() {
        return bracket.iterator();
    }
    
    public void draw(SVGGraphics2D g) {
        // TODO!
    }

    public void addObserver(Observer o) {
        bracket.addObserver(o);
    }
    
}