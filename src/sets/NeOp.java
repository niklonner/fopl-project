package sets;

public class NeOp extends Operator {
    public boolean apply(Number d1, Double d2) {
        if (d1 == null)
            return false;
        Double d = d1.doubleValue();
        return !d.equals(d2);
    }
}