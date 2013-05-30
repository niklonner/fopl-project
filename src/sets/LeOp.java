package sets;

public class LeOp extends Operator {
    public boolean apply(Number d1, Double d2) {
        if (d1 == null)
            return false;
        Double d = d1.doubleValue();
        return d.compareTo(d2) <= 0;
    }
}