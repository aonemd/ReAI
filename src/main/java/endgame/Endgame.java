package endgame;

import java.util.HashSet;

import search.SearchProblem;
import search.strategy.SearchStrategy;

public class Endgame extends SearchProblem {
    private static char[][] grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private HashSet<Cell> stonePositions;
    private HashSet<Cell> warriorPositions;

    public Endgame() {
    }

    public Endgame(String grid) {
        this.parse(grid);
    }

    public String solve(String grid, SearchStrategy searchStrategy, boolean visualize) {
        this.parse(grid);

        var goalNode = this.search(searchStrategy);

        if (goalNode != null) {
            if (visualize) {
                int m = this.grid.length, n = this.grid[0].length;
                ConsoleVisualizer.run(goalNode, m, n);
            }

            return goalNode.toPlan() + ";" + goalNode.pathCost() + ";" + this.expandedNodesCount;
        }

        return "There is no solution";
    }

    private void parse(String input) {
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
}
