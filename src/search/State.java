package search;

import java.util.List;

public interface State {
    public List<Operator> validOperators();
    public State clone();
    public State applyOperator(String operatorName);
}
