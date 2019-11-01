package search;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import static util.Inspector.inspect;

public abstract class SearchProblem {
    public HashMap<String, Operator> operators;
    public State initialState;

    public static String search(SearchProblem searchProblem, String strategy) {
        List<SearchTreeNode> nodeList = new SearchTreeNode(searchProblem.initialState, null, null, 0, 0).expand();

        List<State> visitedStates = new ArrayList<State>();

        SearchTreeNode currentNode = null;
        Iterator<SearchTreeNode> nodeListIterator = nodeList.iterator();
        while (nodeListIterator.hasNext()) {
            currentNode = nodeList.remove(0);

            if (currentNode.state.isGoal()) {
                return currentNode.toPlan();
            } else {
                if (visitedStates.contains(currentNode.state)) {
                    continue;
                }

                nodeList.addAll(currentNode.expand());
            }

            visitedStates.add(currentNode.state);
        }

        return "There is no solution";
    }
}
