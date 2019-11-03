package search;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;
import static util.Inspector.inspect;

public abstract class SearchProblem {
    public HashMap<String, Operator> operators;
    public State initialState;

    public static String search(SearchProblem searchProblem, String strategy) {
        List<SearchTreeNode> nodeList = new SearchTreeNode(searchProblem.initialState,
                null,
                null,
                0,
                searchProblem.initialState.getCost())
            .expand();
        int expandedNodeCount = 1;

        Set<State> visitedStates = new HashSet<State>();

        SearchTreeNode currentNode = null;
        Iterator<SearchTreeNode> nodeListIterator = nodeList.iterator();
        while (nodeListIterator.hasNext()) {
            currentNode = nodeList.remove(0);

            if (currentNode.state.isGoal()) {
                return currentNode.toPlan() + ";" + expandedNodeCount;
            } else if (!currentNode.state.isDead()) {
                if (visitedStates.contains(currentNode.state)) {
                    continue;
                }

                List<SearchTreeNode> _expandedNodes = currentNode.expand();
                expandedNodeCount += 1;
                switch (strategy) {
                    case "BF":
                        nodeList.addAll(_expandedNodes);
                        break;
                    case "DF":
                        _expandedNodes.addAll(nodeList);
                        nodeList = _expandedNodes;
                    case "UC":
                        nodeList.addAll(_expandedNodes);
                        Collections.sort(nodeList);
                        break;
                }
            }

            visitedStates.add(currentNode.state);
        }

        return "There is no solution";
    }
}
