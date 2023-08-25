package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.Tuple;

import java.util.HashSet;

public record SearchTreeNode(State state, SearchTreeNode parent, Operator operator, int depth, int pathCost) {
    public List<SearchTreeNode> expand(List<Operator> operators, HashSet<State> visited) {
        List<SearchTreeNode> nodes = new ArrayList<>();

        // calculate state cost before applying the operator
        // ...and add operator cost after applying the operator
        //
        int stateCost = this.state().calculateStateCost();

        for (Operator op : operators) {
            Tuple<State, Integer> operatorResult = op.apply(this.state());
            State newState = operatorResult.first();
            int appliedOperatorCost = operatorResult.second();

            if (newState.valid()) {
                var newPathCost = this.pathCost() + stateCost + appliedOperatorCost;

                var nxtNode = new SearchTreeNode(newState, this, op, depth() + 1, newPathCost);

                nodes.add(nxtNode);
            }
        }

        return nodes;
    }

    public int f() {
        // f() = g() + h()
        return pathCost() + this.state().calculateHeuristicFuncCost();
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
