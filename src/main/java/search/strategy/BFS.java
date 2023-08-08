package search.strategy;

import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public class BFS implements SearchStrategy {
    @Override
    public void addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        newNodes.forEach((newNode) -> que.offerLast(newNode));
    }
}
