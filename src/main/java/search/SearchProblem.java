package search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import search.strategy.*;

public abstract class SearchProblem {
    public List<Operator> operators;
    public State initialState;
    public int expandedNodesCount = 0;

    public abstract String solve(String grid, SearchStrategy searchStrategy, boolean visualize);

    public SearchTreeNode search(SearchStrategy searchStrategy) {
        SearchTreeNode curNode = new SearchTreeNode(this.initialState, null, null, 0, 0);

        Deque<SearchTreeNode> que = new ArrayDeque<>();
        que.offer(curNode);

        HashSet<State> visited = new HashSet<>();

        while (!que.isEmpty()) {
            curNode = que.poll();

            // outline
            // if not visited:
            //   visit
            //   mark as visited
            //   expand neighbors:
            //      if neighbor is not visited:
            //          queue append neighbor

            // if (!visited.add(curNode.state())) {
            //     continue;
            // }

            if (curNode.state().goal()) {
                return curNode;
            }

            var expandedNodes = curNode.expand(this.operators, visited);
            // System.out.println("expandedNodes:");
            // expandedNodes.stream().forEach(n -> System.out.println(n.toPlan() + " : " + n.depth()));
            // System.out.println("*********************************");
            expandedNodes.removeIf(expandedNode -> (!visited.add(expandedNode.state())));

            que = searchStrategy.addNodes(que, expandedNodes);

            // System.out.println("que:");
            // que.stream().forEach(n -> System.out.println(n.toPlan() + " : " + n.depth()));
            // System.out.println("+++++++++++++++++++++++++++++++++");

            if (searchStrategy instanceof IDS && que.isEmpty() && expandedNodes.isEmpty()) {
                visited.clear();
                que.offer(new SearchTreeNode(this.initialState, null, null, 0, 0));

                // int level = ((IDS) searchStrategy).level;
                // System.out.println("---------------- new level:" + level + " ---------------");
            }

            this.expandedNodesCount++;
        }

        return null;
    }
}
