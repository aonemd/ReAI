package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Tuple;

import java.util.HashSet;

public record SearchTreeNode(State state, SearchTreeNode parent, Operator operator, int depth, int pathCost) {
    static int dirs[][] = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

    public List<SearchTreeNode> expand(List<Operator> operators, HashSet<State> visited, int m, int n) {
        List<SearchTreeNode> nodes = new ArrayList<>();

        // calculate state cost before applying the operator
        // ...and add operator cost after applying the operator
        //
        int stateCost = this.state().calculateStateCost(m, n);

        for (Operator op : operators) {
            Tuple<State, Integer> operatorResult = op.apply(this.state());
            State newState = operatorResult.first();
            int opCost = operatorResult.second();

            if (newState.valid(m, n)) {
                var newPathCost = this.pathCost() + stateCost;
                newPathCost += opCost;

                var nxtNode = new SearchTreeNode(newState, this, op, depth() + 1, newPathCost);

                System.out.println((operator() != null ? operator().name() : "") + "->" + nxtNode.operator().name()
                        + " (" + op.cost() + "|" + stateCost + ") " + nxtNode.pathCost() + ", depth: " + nxtNode.depth());

                nodes.add(nxtNode);
            }
        }

        return nodes;
    }

    public String toPlan() {
        List<String> planOperators = new ArrayList<String>();

        SearchTreeNode current = this;
        while (current.parent() != null) {
            planOperators.add(current.operator().name());

            current = current.parent();
        }

        Collections.reverse(planOperators);

        return String.join(",", planOperators);
    }
}
