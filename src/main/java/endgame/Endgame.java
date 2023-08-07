package endgame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import util.Tuple;
import search.Operator;
import search.SearchTreeNode;
import search.State;



public class Endgame /* extends SearchProblem */ {
    private static char[][] grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private HashSet<Cell> stonePositions;
    private HashSet<Cell> warriorPositions;
    private List<Operator> operators;

    static int dirs[][] = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

    public Endgame(String input) {
        // parse grid into useful information
        String[] gridInfo = input.split(";");

        String[] gridDimensions = gridInfo[0].split(",");
        this.gridWidth = Integer.parseInt(gridDimensions[0]);
        this.gridHeight = Integer.parseInt(gridDimensions[1]);

        this.grid = new char[gridWidth][gridHeight];

        String[] ironManXYPosition = gridInfo[1].split(",");
        int ironManX = Integer.parseInt(ironManXYPosition[0]);
        int ironManY = Integer.parseInt(ironManXYPosition[1]);
        this.ironManPosition = new Cell(ironManX, ironManY);
        grid[ironManX][ironManY] = 'I';

        String[] thanosXYPosition = gridInfo[2].split(",");
        int thanosX = Integer.parseInt(thanosXYPosition[0]);
        int thanosY = Integer.parseInt(thanosXYPosition[1]);
        this.thanosPosition = new Cell(thanosX, thanosY);
        grid[thanosX][thanosY] = 'T';

        this.stonePositions = new HashSet<Cell>();
        if (gridInfo.length > 3) {
            String[] stoneXYPositions = gridInfo[3].split(",");
            for (int i = 0; i < stoneXYPositions.length; i += 2) {
                int stoneX = Integer.parseInt(stoneXYPositions[i]);
                int stoneY = Integer.parseInt(stoneXYPositions[i + 1]);
                this.stonePositions.add(new Cell(stoneX, stoneY));
                grid[stoneX][stoneY] = 'S';
            }
        }

        this.warriorPositions = new HashSet<Cell>();
        if (gridInfo.length > 4) {
            String[] warriorXYPositions = gridInfo[4].split(",");
            for (int i = 0; i < warriorXYPositions.length; i += 2) {
                int warriorX = Integer.parseInt(warriorXYPositions[i]);
                int warriorY = Integer.parseInt(warriorXYPositions[i + 1]);
                this.warriorPositions.add(new Cell(warriorX, warriorY));

                grid[warriorX][warriorY] = 'W';
            }
        }

        this.operators = OperatorsBuilder.build(this.gridHeight, this.gridWidth);
        this.initialState = new EndGameState(false, ironManPosition, thanosPosition, stonePositions, warriorPositions);
    }

    public static String solve(String grid, String strategy, boolean visualize) {
        Endgame endGameProblem = new Endgame(grid);

        // return search(endGameProblem, strategy);
        return "";
    }

    public static void main(String[] args) {
        // String input = "2,2;0,0;1,0;1,1;0,1";
        // String input = "2,2;0,0;1,0";
		String input = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
        // String solution = Endgame.solve(input, "BF", false);

        Endgame endGameProblem = new Endgame(input);
        int m = endGameProblem.grid.length, n = endGameProblem.grid[0].length;

        // print grid before
        printGrid(endGameProblem.grid);

        HashSet<State> visited = new HashSet<>();

        var initState = new EndGameState(
                false,
                endGameProblem.ironManPosition,
                endGameProblem.thanosPosition,
                endGameProblem.stonePositions,
                endGameProblem.warriorPositions);
        SearchTreeNode curNode = new SearchTreeNode(initState, null, null, 0, 0);
        SearchTreeNode goalNode = null;

        Queue<SearchTreeNode> que = new LinkedList<>();
        que.offer(curNode);

        while (!que.isEmpty()) {
            curNode = que.poll();

            if (!visited.add(curNode.state())) continue;

            if (((EndGameState)curNode.state()).snapped()) {
                goalNode = curNode;
                break;
            }

            for (SearchTreeNode nxtNode : curNode.expand(endGameProblem.operators, visited, m, n)) {
                que.offer(nxtNode);
            }

            if (goalNode != null) {
                break;
            }

            System.out.println();
            System.out.println("snapped: " + curNode.state().goal());
            System.out.println("score: " + curNode.pathCost());
            System.out.println("--------------------");
        }

        if (goalNode != null) {
            System.out.println("-+-+-+-+-+-+-+-+-+-+-");
            System.out.println("snapped: " + goalNode.state().goal());
            System.out.println(goalNode.toPlan() + ";" + goalNode.pathCost() + ";" + goalNode.depth());
            System.out.println("-+-+-+-+-+-+-+-+-+-+-");
            printGrid(endGameProblem.grid);
        }
    }

    static void printGrid(char[][] grid) {
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
