package endgame;

import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;

import search.SearchProblem;
import search.Operator;

public class Endgame extends SearchProblem {
    private char[][] grid;
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private List<Cell> stonePositions;
    private List<Cell> warriorPositions;

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

        this.stonePositions = new ArrayList<Cell>();
        if (gridInfo.length > 3) {
            String[] stoneXYPositions = gridInfo[3].split(",");
            for (int i = 0; i < stoneXYPositions.length; i += 2) {
                int stoneX = Integer.parseInt(stoneXYPositions[i]);
                int stoneY = Integer.parseInt(stoneXYPositions[i + 1]);
                this.stonePositions.add(new Cell(stoneX, stoneY));
                grid[stoneX][stoneY] = 'S';
            }
        }

        this.warriorPositions = new ArrayList<Cell>();
        if (gridInfo.length > 4) {
            String[] warriorXYPositions = gridInfo[4].split(",");
            for (int i = 0; i < warriorXYPositions.length; i += 2) {
                int warriorX = Integer.parseInt(warriorXYPositions[i]);
                int warriorY = Integer.parseInt(warriorXYPositions[i + 1]);
                this.warriorPositions.add(new Cell(warriorX, warriorY));

                grid[warriorX][warriorY] = 'W';
            }
        }

        HashMap<String, Operator> operators = new HashMap<String, Operator>();
        operators.put("snap", new Operator("snap", 5));
        operators.put("collect", new Operator("collect", 3));
        operators.put("kill", new Operator("kill", 2));
        operators.put("up", new Operator("up", 0));
        operators.put("down", new Operator("down", 0));
        operators.put("right", new Operator("right", 0));
        operators.put("left", new Operator("left", 0));

        this.operators = operators;
        this.initialState = new EndGameState(gridWidth,
                gridHeight,
                ironManPosition,
                thanosPosition,
                new HashSet<Cell>(stonePositions),
                new HashSet<Cell>(warriorPositions),
                operators,
                0,
                false);
    }

    public static String solve(String grid, String strategy, boolean visualize) {
        Endgame endGameProblem = new Endgame(grid);

        // return search(endGameProblem, strategy);
        return "";
    }

    public static void main(String[] args) {
        // String input = "2,2;0,0;1,0;1,1;0,1";
        String input = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
        // String solution = Endgame.solve(input, "BF", false);

        Endgame endGameProblem = new Endgame(input);
        int m = 5, n = 5;

        // print grid before
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char out;
                if (endGameProblem.grid[i][j] != 0) {
                    out = endGameProblem.grid[i][j];
                } else {
                    out = '-';
                }
                System.out.print(out + " ");
            }
            System.out.println();
        }
        System.out.println();

        int cost = 0;

        boolean[][] visited = new boolean[m][n];

        Queue<Cell> que = new LinkedList<>();
        que.offer(endGameProblem.ironManPosition);

        int dirs[][] = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };
        while (!que.isEmpty()) {
            Cell cur = que.poll();

            for (String opName : endGameProblem.operators.keySet()) {
                Operator op = endGameProblem.operators.get(opName);

                Cell nxt = cur;
                switch (op.name) {
                    case "up":
                        nxt = new Cell(cur.x + 1, cur.y + 0);
                        break;
                    case "down":
                        nxt = new Cell(cur.x + -1, cur.y + 0);
                        break;
                    case "left":
                        nxt = new Cell(cur.x + 0, cur.y + -1);
                        break;
                    case "right":
                        nxt = new Cell(cur.x + 0, cur.y + 1);
                        break;
                    case "collect":
                        if (endGameProblem.grid[nxt.x][nxt.y] == 'S') {
                            cost += op.cost;
                            endGameProblem.grid[nxt.x][nxt.y] = '-';
                        }
                        break;
                    case "kill":
                        for (int[] dir : dirs) {
                            Cell adjCell = new Cell(cur.x + dir[0], cur.y + dir[1]);

                            if (adjCell.x < 0 || adjCell.x >= m || adjCell.y < 0 || adjCell.y >= n
                                    || visited[adjCell.x][adjCell.y])
                                continue;

                            if (endGameProblem.grid[adjCell.x][adjCell.y] == 'W') {
                                cost += op.cost;
                                endGameProblem.grid[adjCell.x][adjCell.y] = '-';
                            }
                        }
                        break;
                    case "snap":
                        // if Thanos
                        if (endGameProblem.grid[cur.x][cur.y] == 'T') {
                            cost += op.cost;
                        }
                        break;
                    default:
                        nxt = cur;
                        break;
                }

                if (nxt.x < 0 || nxt.x >= m || nxt.y < 0 || nxt.y >= n || visited[nxt.x][nxt.y])
                    continue;

                System.out.println(opName);

                que.offer(nxt);
            }

            visited[cur.x][cur.y] = true;
        }

        // print grid after
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char out;
                if (endGameProblem.grid[i][j] != 0) {
                    out = endGameProblem.grid[i][j];
                } else {
                    out = '-';
                }
                System.out.print(out + " ");
            }
            System.out.println();
        }

        System.out.println("\nscore:" + " " + cost);
    }
}
