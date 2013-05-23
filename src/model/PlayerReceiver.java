package model;

public interface PlayerReceiver<ResultType> {
    public void acceptPlayer(Player<ResultType> p);
}
