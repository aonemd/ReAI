package search;

import java.util.List;

public interface State {
    public List<Operator> validOperators();
    public State applyOperator(String operatorName);
    public boolean isGoal();
    public int getCost();
    public State clone();
    public boolean equals(Object other);
    public String toString();
}
