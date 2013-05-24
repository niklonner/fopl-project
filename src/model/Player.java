package model;

import java.util.*;

public class Player<ResultType>
    implements Comparable<Player<ResultType>> {
    private static int nextId = 0;

    private Integer id; // Object so null will be default value.
    private String name;
    private Map<String, Object> attributes = new HashMap<>();
    private PrettyPrinterScore<ResultType> prettyPrinter;

    public Player() {
        this("player " + (nextId));
    }

    public Player(String name) {
        this.id = nextId++;
        this.setName(name);
        //        attributes.put("result", null);
    }

    public void setPrettyPrinter(PrettyPrinterScore<ResultType> prettyPrinter) {
        this.prettyPrinter = prettyPrinter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getPrettyPrintScore();
    }
    
    public Integer getId() {
        return this.id;
    }

    public String getPrettyPrintScore() {
        return prettyPrinter == null ? "player " + getId() + " " + " score " + getResult().toString() : prettyPrinter.prettyPrint(this);
    }
    
    @SuppressWarnings("unchecked")
        public ResultType setResult(ResultType result) {
        return (ResultType) attributes.put("result", result);
    }

    @SuppressWarnings("unchecked")
        public ResultType getResult() {
        Object result = attributes.get("result");
        if (result == null) {
            throw new IllegalStateException("No result is set for player " + name);
        } else {
            return (ResultType) result;
        }
    }

    public void resetResult() {
        attributes.remove("result");
    }

    public boolean resultIsSet() {
        return attributeIsSet("result");
    }

    public Object get(String key) {
        return attributes.get(key);
    }

    public Object set(String key, Object value) {
        return attributes.put(key, value);
    }

    public boolean attributeIsSet(String key) {
        return attributes.get(key) != null;
    }

    @SuppressWarnings("unchecked")
        // Default ordering is highest score first.
        // null (no result attribute set) is infinitely small
        public int compareTo(Player<ResultType> other) {
        int tiebreaker = other.id - id;
        boolean thisSet = resultIsSet();
        boolean otherSet = other.resultIsSet();
        if (!thisSet) {
            return otherSet ? 1 : tiebreaker;
        } else if (!otherSet) {
            return -1;
        } else {
            int res = ((Comparable) getResult()).compareTo((Comparable) other.getResult());
            return res != 0 ? -res : tiebreaker;
        }
    }

    public boolean equals(Object o) {
        if (this==o) {
            return true;
        } else if (o==null || getClass()!=o.getClass()) {
            return false;
        } else {
            return id == ((Player<ResultType>)o).id;
        }
    }
}
