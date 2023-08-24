package endgame;

import java.util.ArrayList;
import java.util.List;

import search.Operator;
import search.State;
import util.Tuple;

public class OperatorsBuilder {
    static int dirs[][] = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

    public static List<Operator> build(int m, int n) {
        List<Operator> operators = new ArrayList<Operator>();

        operators.add(new Operator("COLLECT", 3, (State state) -> {
            EndGameState curState = (EndGameState) state;

            int finalCost = 3;
            if (curState.stonePositions().contains(curState.ironManPosition())) {
                var newState = curState.clone();
                newState.stonePositions().remove(curState.ironManPosition());

                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("SNAP", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newState = curState.clone();
            if (newState.ironManPosition().equals(newState.thanosPosition()) && newState.canSnap()) {
                return new Tuple<State, Integer>(new EndGameState(true, newState.ironManPosition(),
                        newState.thanosPosition(), newState.stonePositions(), newState.warriorPositions()), 0);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("UP", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x - 1, curState.ironManPosition().y + 0);

            int finalCost = 0;
            if (!newCell.inBounds(m, n))
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap())
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(),
                        curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("DOWN", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 1, curState.ironManPosition().y + 0);

            int finalCost = 0;
            if (!newCell.inBounds(m, n))
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap())
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(),
                        curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("RIGHT", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 0, curState.ironManPosition().y + 1);

            int finalCost = 0;
            if (!newCell.inBounds(m, n))
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap())
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(),
                        curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("LEFT", 0, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var newCell = new Cell(curState.ironManPosition().x + 0, curState.ironManPosition().y - 1);

            int finalCost = 0;
            if (!newCell.inBounds(m, n))
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);
            if (newCell.equals(curState.thanosPosition()) && !curState.canSnap())
                return new Tuple<State, Integer>(curState.toEmptyState(), 0);

            if (!curState.warriorPositions().contains(newCell)) {
                EndGameState newState = new EndGameState(curState.snapped(), newCell, curState.thanosPosition(),
                        curState.stonePositions(), curState.warriorPositions());
                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        operators.add(new Operator("KILL", 2, (State state) -> {
            EndGameState curState = (EndGameState) state;

            var cur = curState.ironManPosition();

            int killed = 0;
            var newState = curState.clone();
            for (int[] dir : dirs) {
                Cell adjCell = new Cell(cur.x + dir[0], cur.y + dir[1]);

                if (!adjCell.inBounds(m, n))
                    continue;

                if (curState.warriorPositions().contains(adjCell)) {
                    newState.warriorPositions().remove(adjCell);
                    killed++;
                }
            }

            if (killed > 0) {
                int finalCost = killed * 2;
                return new Tuple<State, Integer>(newState, finalCost);
            }

            return new Tuple<State, Integer>(curState.toEmptyState(), 0);
        }));

        return operators;
    }
}
