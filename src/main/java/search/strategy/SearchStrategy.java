package search.strategy;

import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public interface SearchStrategy {
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes);
}
