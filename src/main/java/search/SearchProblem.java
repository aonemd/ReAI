package search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class SearchProblem {
    public List<Operator> operators;
    public State initialState;

    public SearchTreeNode search() {
        SearchTreeNode curNode = new SearchTreeNode(this.initialState, null, null, 0, 0);

        Queue<SearchTreeNode> que = new LinkedList<>();
        que.offer(curNode);

        HashSet<State> visited = new HashSet<>();

        while (!que.isEmpty()) {
            curNode = que.poll();

            if (!visited.add(curNode.state()))
                continue;

            if (curNode.state().goal()) {
                return curNode;
            }

            for (SearchTreeNode nxtNode : curNode.expand(this.operators, visited)) {
                que.offer(nxtNode);
            }

            System.out.println();
            System.out.println("goal: " + curNode.state().goal());
            System.out.println("score: " + curNode.pathCost());
            System.out.println("--------------------");
        }

        return null;
    }
}
