package search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import search.strategy.*;

public abstract class SearchProblem {
    public List<Operator> operators;
    public State initialState;
    public SearchStrategy searchStrategy = new BFS();

    public SearchTreeNode search() {
        SearchTreeNode curNode = new SearchTreeNode(this.initialState, null, null, 0, 0);

        Deque<SearchTreeNode> que = new ArrayDeque<>();
        que.offer(curNode);

        HashSet<State> visited = new HashSet<>();

        while (!que.isEmpty()) {
            curNode = que.poll();

            if (!visited.add(curNode.state()))
                continue;

            if (curNode.state().goal()) {
                return curNode;
            }

            searchStrategy.addNodes(que, curNode.expand(this.operators, visited));

            System.out.println();
            System.out.println("goal: " + curNode.state().goal());
            System.out.println("score: " + curNode.pathCost());
            System.out.println("--------------------");
        }

        return null;
    }
}
