package search.strategy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import search.SearchTreeNode;

public class IDS extends DFS {
    public int level = 0;

    @Override
    public Deque<SearchTreeNode> addNodes(Deque<SearchTreeNode> que, List<SearchTreeNode> newNodes) {
        // check if current max level is reached & clear expanded nodes since they
        // canntot be expanded/visited further after current level
        // NEW LEVEL should begin soon
        if (!newNodes.isEmpty() && newNodes.get(newNodes.size() - 1).depth() > this.level) {
            newNodes.clear();
        }

        // if the search queue is exhausted && we should being a NEW LEVEL from the
        // pervious condition,
        // start over:
        //   increase max level/depth
        //   reset visited
        //   reset queue
        if (que.isEmpty() && newNodes.isEmpty()) {
            this.level++;
            return new ArrayDeque<SearchTreeNode>();
        }

        return super.addNodes(que, newNodes);
    }
}
