package search;

public interface State {
    public boolean goal();

    public boolean valid(int... params);

    public int calculateStateCost(int... params);

    public State clone();

    public State toEmptyState();

    public boolean equals(Object other);

    public int hashCode();
}
