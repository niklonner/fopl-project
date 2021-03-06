package model;

import java.util.*;
import util.*;
import sets.*;
import parse.*;

import org.apache.batik.svggen.*;

/* Abstract superclass of all subtournaments.
 * The underlying structure (nodes etc.) of a subtournament is not created until startBuild()
 * is called. At that point, all players that will participate in the subtournament MUST have
 * been added.
 * Observers of subtournaments are automatically added as observers of all nodes in the
 * subtournament.
 */

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
    protected boolean isPlayerSource;
    protected String playerSource;

    public abstract void draw(SVGGraphics2D g);
    public abstract void startBuild();
    protected abstract void dummyRun();
    protected abstract void receiveHook(Player<ResultType> p);
    
    // only for use by subclasses
    protected SubTournament() {
        //      this("subtournament " + nextId);
    }

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
        playerSource = builder.subTournament.playerSource;
        isPlayerSource = builder.subTournament.isPlayerSource;
        readPlayers();
    }

    public static abstract class Builder<T extends Builder<T,ResultType>, ResultType> {
        protected SubTournament<ResultType> subTournament;

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
            subTournament.isPlayerSource = true;
            subTournament.playerSource = source;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public Iterator<Node<ResultType>> iterator() {
        return nodes.iterator();
    }

    public int getId() {
            return id;
    }
    
    public void acceptPlayer(Player<ResultType> p) {
        players.add(p);
        receiveHook(p);
    }

    public SortedSet<Player<ResultType>> getPlayers() {
        SortedSet<Player<ResultType>> ret = new TreeSet(comp);
        ret.addAll(players);
        return ret;
    }

    protected void readPlayers() {
        if (isPlayerSource) {
            PlayerParser pp = new PlayerParser();
            List<Player> players = (List<Player>) pp.parse(playerSource);
            for (Player p : players) {
                acceptPlayer((Player<ResultType>) p);
                if (pps != null) {
                    p.setPrettyPrinter(pps);
                }
            }
        }
    }

    public void addObserver(Observer o) {
        super.addObserver(o);
        for (Node<ResultType> n : nodes) {
            n.addObserver(o);
        }
    }
    
}
