package endgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import search.SearchTreeNode;

public class ConsoleVisualizer {
    public static void run(SearchTreeNode goalNode, int m, int n) {
        List<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();

        var current = goalNode;
        while (current.parent() != null) {
            nodes.add(current);

            current = current.parent();
        }
        Collections.reverse(nodes);

        for (var node : nodes) {
            EndGameState state = (EndGameState) node.state();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.print("\033[H\033[2J"); // clear console

            System.out.println(node.operator().name() + "(" + node.pathCost() + ")");

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    var curCell = new Cell(i, j);
                    char out = 0;

                    if (state.stonePositions().contains(curCell))
                        out = 'S';
                    if (state.warriorPositions().contains(curCell))
                        out = 'W';
                    if (state.thanosPosition().equals(curCell))
                        out = 'T';
                    if (state.ironManPosition().equals(curCell))
                        out = out == 0 ? 'I' : 'X';
                    if (out == 0)
                        out = '-';

                    System.out.print(out + " ");
                }
                System.out.println();
            }
        }
    }

    public static void printGrid(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char out;
                if (grid[i][j] != 0) {
                    out = grid[i][j];
                } else {
                    out = '-';
                }
                System.out.print(out + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
