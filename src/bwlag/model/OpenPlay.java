package bwlag.model;

import model.*;
import sets.*;
import java.util.*;

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
        for (Player<ResultType> p : tmpReceiveHook) {
            receiveHook(p);
        }
        for (Player<ResultType> p : tmpAcceptPlayer) {
            acceptPlayer(p);
        }
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
}