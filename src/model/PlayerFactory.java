package model;

/*
 * Standard Player factory. Should be passed to tournament creation.
 * If other type of Player is required, subclass this class.
 */
public class PlayerFactory<ResultType> {
    public Player<ResultType> makePlayer() {
	return new Player<ResultType>();
    }
}
