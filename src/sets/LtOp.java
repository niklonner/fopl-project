package sets;

public class LtOp extends Operator {
    public boolean apply(Number d1, Double d2) {
        double d = d1==null ? 0 : d1.doubleValue();
        return d < d2;
    }
}