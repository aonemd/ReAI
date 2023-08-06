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

        int m = grid.length, n = grid[0].length;
        var operators = new ArrayList<Operator>();

        operators.add(new Operator("UP", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x - 1, curState.ironManPosition().y + 0);

            int finalCost = 0;
            if (!newCell.inBounds(m, n)) return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap()) return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(), curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("DOWN", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 1, curState.ironManPosition().y + 0);

            int finalCost = 0;
            if (!newCell.inBounds(m, n)) return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap()) return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(), curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("RIGHT", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 0, curState.ironManPosition().y + 1);

            int finalCost = 0;
            if (!newCell.inBounds(m, n)) return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap()) return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(), curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("LEFT", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 0, curState.ironManPosition().y - 1);

            int finalCost = 0;
            if (!newCell.inBounds(m, n)) return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap()) return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(), curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("COLLECT", 3, (State state) -> {
            EndGameState curState = (EndGameState) state;

            int finalCost = 3;
            if (curState.stonePositions().contains(curState.ironManPosition())) {
                var newState = curState.clone();
                newState.stonePositions().remove(curState.ironManPosition());

                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("KILL", 2, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var cur = curState.ironManPosition();

            int killed = 0;
            var newState = curState.clone();
            for (int[] dir : dirs) {
                Cell adjCell = new Cell(cur.x + dir[0], cur.y + dir[1]);

                if (!adjCell.inBounds(m, n)) continue;

                if (curState.warriorPositions().contains(adjCell)) {
                    newState.warriorPositions().remove(adjCell);
                    killed++;
                }
            }

            if (killed > 0) {
                int finalCost = killed * 2;
                return new Tuple<State,Integer>(newState, finalCost);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("SNAP", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newState = curState.clone();
            if (newState.ironManPosition().equals(newState.thanosPosition()) && newState.canSnap()) {
                return new Tuple<State,Integer>(new EndGameState(true, newState.ironManPosition(), newState.thanosPosition(), newState.stonePositions(), newState.warriorPositions()), 0);
            }

            return new Tuple<State,Integer>(curState.toEmptyState(), 0);
        }));


        this.operators = operators;
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
