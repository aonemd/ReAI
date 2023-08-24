package search.strategy;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public class DFS implements SearchStrategy {
    @Override
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        Collections.reverse(newNodes);
        newNodes.forEach((newNode) -> que.offerFirst(newNode));

        return que;
    }
}
