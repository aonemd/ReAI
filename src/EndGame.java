package endgame;

import java.util.List;
import java.util.ArrayList;

import search.SearchProblem;

public class EndGame extends SearchProblem {
    private String grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private List<Cell> stonePositions;
    private List<Cell> warriorPositions;

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

        return search(endGameProblem , strategy);
    }

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

	public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
