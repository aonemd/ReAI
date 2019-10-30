package search;

import java.util.List;

public interface State {
    public List<String> validOperatorNames();
    public State clone();
}
