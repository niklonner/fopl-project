package model;

/* Known implementing classes: SubTournament, Node.
 */

public interface PlayerReceiver<ResultType> {
    public void acceptPlayer(Player<ResultType> p);
    public int getId();
}
