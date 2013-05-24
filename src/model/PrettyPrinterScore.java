package model;

public interface PrettyPrinterScore<ResultType> {
    public String prettyPrint(Player<ResultType> p);
}