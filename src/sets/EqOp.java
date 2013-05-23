package sets;

public class EqOp extends Operator {
    public boolean apply(Number d1, Double d2) {
        Double d = d1==null ? 0 : d1.doubleValue();
        return d.equals(d2);
    }
}