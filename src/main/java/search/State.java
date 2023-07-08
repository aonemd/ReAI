package search;

import java.util.List;

public interface State {
    public List<Operator> validOperators();
    public State applyOperator(String operatorName);
    public boolean isGoal();
    public boolean isDead();
    public int getCost();
    public double hfI();
    public State clone();
    public String toString();
    public boolean equals(Object other);
    public int hashCode();
}
