package search;

import java.util.HashMap;

public abstract class SearchProblem {
    public HashMap<String, Operator> operators;
    public State initialState;

    // General Search
    public static String search(SearchProblem searchProblem, String strategy) {
        // convert initial state into a node
        // add this node to nodes list
        // loop over nodes
        //   node = removeFront(nodes)
        //   return node if node is goals <3
        //   nodes << node.extend()
        // end loop

        return "";
    }

}
