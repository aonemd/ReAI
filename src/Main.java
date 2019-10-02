package endgame;

import java.util.ArrayList;

public class Main {
    public static String solve(String grid, String strategy, boolean visualize) {
        // parse grid into useful information
        String[] gridInfo       = grid.split(";");

        String[] gridDimensions = gridInfo[0].split(",");
        String gridWidth        = gridDimensions[0];
        String gridHeight       = gridDimensions[1];

        String[] ironManPosition = gridInfo[1].split(",");
        String ironManX          = ironManPosition[0];
        String ironManY          = ironManPosition[1];

        String[] thanosPosition = gridInfo[2].split(",");
        String thanosX          = thanosPosition[0];
        String thanosY          = thanosPosition[1];

        ArrayList<String[]> stonePositions = new ArrayList<String[]>();
        String[] stoneXYPositions = gridInfo[3].split(",");
        for(int i = 0; i < stoneXYPositions.length; i += 2) {
            String[] stonePosition = {stoneXYPositions[i], stoneXYPositions[i+1]};
            stonePositions.add(stonePosition);
        }

        ArrayList<String[]> warriorPositions = new ArrayList<String[]>();
        String[] warriorXYPositions = gridInfo[4].split(",");
        for(int i = 0; i < warriorXYPositions.length; i += 2) {
            String[] warriorPosition = {warriorXYPositions[i], warriorXYPositions[i+1]};
            warriorPositions.add(warriorPosition);
        }

        return search(grid, strategy);
    }

	public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
