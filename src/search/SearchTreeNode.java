package search;

public class SearchTreeNode {
    public State state;
    public SearchTreeNode parent;
    public Operator operator;
    public int depth;
    public int pathCost;

    public SearchTreeNode(State state, SearchTreeNode parent, Operator operator,
            int depth, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.operator = operator;
        this.depth = depth;
        this.pathCost = pathCost;
    }
}
