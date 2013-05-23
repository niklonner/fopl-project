package sets;

public class GtOp extends Operator {
    public boolean apply(Number d1, Double d2) {
        double d = d1==null ? 0 : d1.doubleValue();
        System.out.println(d1 + " " + d2);
        return d > d2;
    }
}