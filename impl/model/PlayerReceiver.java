package generic;

public interface PlayerReceiver<ResultType extends Comparable<? super ResultType>> {
    public void acceptPlayer(Player<ResultType> p);
}