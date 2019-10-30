package search;

import java.util.List;
import java.util.ArrayList;

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

    public List<SearchTreeNode> expand() {
        List<SearchTreeNode> expandedNodes = new ArrayList<SearchTreeNode>();
        for (Operator operator : this.state.validOperators()) {
            State newState = this.state.clone().applyOperator(operator.name);
            SearchTreeNode expandedNode = new SearchTreeNode(newState, this, operator, this.depth + 1, this.pathCost);

            expandedNodes.add(expandedNode);
        }

        return expandedNodes;
    }
}
