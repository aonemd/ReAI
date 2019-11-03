package search;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SearchTreeNode implements Comparable {
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
            SearchTreeNode expandedNode = new SearchTreeNode(newState, this, operator, this.depth + 1, newState.getCost());

            expandedNodes.add(expandedNode);
        }

        return expandedNodes;
    }

    public String toPlan() {
        List<String> planOperators = new ArrayList<String>();
        SearchTreeNode current = this;
        while (current.parent != null) {
            planOperators.add(current.operator.name);

            current = current.parent;
        }

        Collections.reverse(planOperators);

        return String.join(",", planOperators);
    }

    @Override
    public int compareTo(Object other) {
        if (this == other) {
            return 0;
        }

        SearchTreeNode otherNode = (SearchTreeNode) other;

        return new Integer(this.pathCost).compareTo(otherNode.pathCost);
    }
}
