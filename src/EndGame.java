package endgame;

import java.util.ArrayList;

public class EndGame {
    private String grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private ArrayList<Cell> stonePositions;
    private ArrayList<Cell> warriorPositions;

    public EndGame(String grid) {
        // parse grid into useful information
        String[] gridInfo       = grid.split(";");

        String[] gridDimensions = gridInfo[0].split(",");
        this.gridWidth = Integer.parseInt(gridDimensions[0]);
        this.gridHeight = Integer.parseInt(gridDimensions[1]);

        String[] ironManPosition = gridInfo[1].split(",");
        this.ironManPosition = new Cell(Integer.parseInt(ironManPosition[0]),
                Integer.parseInt(ironManPosition[1]));

        String[] thanosPosition = gridInfo[2].split(",");
        this.thanosPosition = new Cell(Integer.parseInt(thanosPosition[0]),
                Integer.parseInt(thanosPosition[1]));

        this.stonePositions = new ArrayList<Cell>();
        String[] stoneXYPositions = gridInfo[3].split(",");
        for(int i = 0; i < stoneXYPositions.length; i += 2) {
            this.stonePositions.add(new Cell(Integer.parseInt(stoneXYPositions[i]),
                        Integer.parseInt(stoneXYPositions[i+1])));
        }

        this.warriorPositions = new ArrayList<Cell>();
        String[] warriorXYPositions = gridInfo[4].split(",");
        for(int i = 0; i < warriorXYPositions.length; i += 2) {
            this.warriorPositions.add(new Cell(Integer.parseInt(warriorXYPositions[i]),
                        Integer.parseInt(warriorXYPositions[i+1])));
        }

    }

    public static String solve(String grid, String strategy, boolean visualize) {
        EndGame endGameProblem = new EndGame(grid);

        return search(grid, strategy);
    }

	public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
