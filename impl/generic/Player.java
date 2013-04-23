package generic;

public class Player {
    private String name;
    private int result;
    private boolean resultSet;

    public Player(String name) {
	this.name = name;
    }
    
    public String getName() {
	return name;
    }

    public void setResult(int result) {
	this.result = result;
    }

    public boolean resultIsSet() {
	return resultSet;
    }

    public int getResult() {
	return result;
    }
}