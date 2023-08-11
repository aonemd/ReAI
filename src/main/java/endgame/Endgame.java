package endgame;

import java.util.HashSet;

import search.SearchProblem;

public class Endgame extends SearchProblem {
    private static char[][] grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private HashSet<Cell> stonePositions;
    private HashSet<Cell> warriorPositions;

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

        var goalNode = endGameProblem.search();

        printGrid(endGameProblem.grid);
        if (goalNode != null) {
            return goalNode.toPlan() + ";" + goalNode.pathCost() + ";" + endGameProblem.expandedNodesCount;
        }

        return "There is no solution";
    }

    public static void main(String[] args) {
        String input = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
        // String input = "3,3;0,0;1,0;0,2;2,0";
        // String input = "2,2;0,0;1,0;1,1;0,1";
        // String input = "2,2;0,0;1,0;1,1;0,1";

        long startTime = System.nanoTime();

        String solution = Endgame.solve(input, "BF", false);
        System.out.println(solution);

        long endTime = System.nanoTime();
        long timeElapsed = (endTime - startTime) / 1000000;
        System.out.println("Time: " + timeElapsed + "ms");
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
