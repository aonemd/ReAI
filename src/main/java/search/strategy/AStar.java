package search.strategy;

import java.util.Deque;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import search.SearchTreeNode;

public class AStar implements SearchStrategy {
    @Override
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        newNodes.forEach((newNode) -> que.offerLast(newNode));

        SearchTreeNode[] a = que.toArray(new SearchTreeNode[0]);
        Arrays.sort(a, (n1, n2) -> n1.f() - n2.f());
        que.clear();
        Collections.addAll(que, a);

        return que;
    }
}
