package generic;

import java.util.*;

public class Player<ResultType extends Comparable<? super ResultType>>
    implements Comparable<Player<ResultType>> {
    private static int nextId = 0;

    private Integer id; // Object so null will be default value.
    private String name;
    private Map<String, Object> attributes = new HashMap<>();


    public Player() {
        this("player " + (nextId));
    }

    public Player(String name) {
        this.id = nextId++;
        this.setName(name);
        //        attributes.put("result", null);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return this.id;
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

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public Object set(String key, Object value) {
        return attributes.put(key, value);
    }

    public boolean attributeIsSet(String key) {
        return attributes.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    // Default ordering is highest score first.
    public int compareTo(Player<ResultType> other) {
        return ((ResultType) attributes.get("result")).compareTo((ResultType) other.attributes.get("result"));
    }
}
