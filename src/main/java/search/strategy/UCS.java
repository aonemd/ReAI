package search.strategy;

import java.util.Deque;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import search.SearchTreeNode;

public class UCS implements SearchStrategy {
    @Override
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        newNodes.forEach((newNode) -> que.offerLast(newNode));

        // TODO: use a priority queue or something better than this
        // sort
        SearchTreeNode[] a = que.toArray(new SearchTreeNode[0]);
        Arrays.sort(a, (n1, n2) -> n1.pathCost() - n2.pathCost());
        que.clear();
        Collections.addAll(que, a);

        return que;
    }
}
