package search.strategy;

import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public class BFS implements SearchStrategy {
    @Override
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        newNodes.forEach((newNode) -> que.offerLast(newNode));

        return que;
    }
}
