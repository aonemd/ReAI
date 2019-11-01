package search;

public class Operator {
    public String name;
    public int cost;

    public Operator(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String toString() {
        return this.name + "(" + this.cost + ")";
    }
}
