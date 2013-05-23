package model;

public class RndDefault implements RandomGenerator<Integer> {
    private static java.util.Random rnd = new java.util.Random();
    
    public Integer next() {
        return rnd.nextInt(100);
    }       
}