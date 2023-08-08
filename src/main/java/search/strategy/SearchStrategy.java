package search.strategy;

import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public interface SearchStrategy {
    public void addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes);
}
