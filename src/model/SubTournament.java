package model;

import java.util.*;
import util.*;
import sets.*;
import parse.*;

public abstract class SubTournament<ResultType>
    extends Observable implements PlayerReceiver<ResultType>, Iterable<Node<ResultType>> {
    private static int nextId = 0;
    private int id;
    protected String name;
    protected SortedSet<Player<ResultType>> players = new TreeSet<>();
    protected List<Node<ResultType>> nodes = new ArrayList<>();
    protected List<Observer> observers = new ArrayList<>();
    protected Comparator<Player<ResultType>> comp;
    protected RandomGenerator<ResultType> rnd;
    protected Tournament tournament;
    protected PrettyPrinterScore<ResultType> pps;

    public abstract void draw(SVGGraphics2D g);

    // only for use by subclasses
    protected SubTournament() {
        //      this("subtournament " + nextId);
    }

    //
    // protected SubTournament(String name) {
    //  id = nextId++;
    //  this.name = name;
    //  players = new ArrayList<>();
    //  nodes = new ArrayList<>();
    // }

    protected SubTournament(Builder<?,ResultType> builder) {
        id = nextId++;
        comp = builder.subTournament.comp;
        if (comp != null) {
            players = new TreeSet<Player<ResultType>>(comp);
            players.addAll(builder.subTournament.players);
        } else {
            players = builder.subTournament.players;
        }
        rnd = builder.subTournament.rnd;
        if (rnd == null) {
            rnd = (RandomGenerator)new RndDefault();
        }
        observers = builder.subTournament.observers;
        for (Observer o : observers) {
            addObserver(o);
        }
        tournament = builder.subTournament.tournament;
        pps = builder.subTournament.pps;
        if (builder.playerSource != null) {
            PlayerParser pp = new PlayerParser();
            List<Player> players = (List<Player>) pp.parse(builder.playerSource);
            for (Player p : players) {
                acceptPlayer((Player<ResultType>) p);
                if (pps != null) {
                    p.setPrettyPrinter(pps);
                }
            }
        }
    }

    public static abstract class Builder<T extends Builder<T,ResultType>, ResultType> {
        protected SubTournament<ResultType> subTournament;
        protected String playerSource;

        public Builder() {
            subTournament = createSubTournament();
        }

        protected abstract SubTournament<ResultType> createSubTournament();
        protected abstract T me();
        public abstract SubTournament<ResultType> getSubTournament();

        public T setPlayers(SortedSet<Player<ResultType>> players) {
            if (players != null) {
                subTournament.players = new TreeSet<>(players);
            }
            return me();
        }

        public T setObservers(List<Observer> observers) {
            if (observers != null) {
                subTournament.observers = new ArrayList<>(observers);
            }
            return me();
        }

        public T setTournament(Tournament tournament) {
            subTournament.tournament = tournament;
            return me();
        }
        
        public T playerSource(String source) {
            playerSource = source;
            return me();
        }

        public T setPrettyPrinter(PrettyPrinterScore<ResultType> pps) {
            subTournament.pps = pps;
            return me();
        }
        
        public T setComparator(Comparator<Player<ResultType>> comp) {
            subTournament.comp = comp;
            return me();
        }

        public T setRandomGenerator(RandomGenerator<ResultType> rnd) {
            subTournament.rnd = rnd;
            return me();
        }

        
    }

    public Iterator<Node<ResultType>> iterator() {
        return nodes.iterator(); // TODO: implement topological sort
    }

    public int getId() {
            return id;
    }
    
    public abstract void startBuild();

    protected abstract void dummyRun();

    protected abstract void receiveHook(Player<ResultType> p);

    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
        receiveHook(p);
    }

}
