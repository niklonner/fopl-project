package sets;

public abstract class Operator {
    public static final double EPS = 1e-6;
    
    public abstract boolean apply(Number d1, Double d2);

    protected boolean anyNull(Double d1, Double d2) {
        return d1==null || d2==null;
    }
}
