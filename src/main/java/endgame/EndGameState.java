package endgame;

import java.util.HashSet;

import search.State;

public class EndGameState implements State {
    private boolean snapped;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private HashSet<Cell> stonePositions;
    private HashSet<Cell> warriorPositions;

    static int dirs[][] = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 } };

    public EndGameState(boolean snapped, Cell ironManPosition, Cell thanosPosition, HashSet<Cell> stonePositions,
            HashSet<Cell> warriorPositions) {
        this.snapped = snapped;
        this.ironManPosition = ironManPosition;
        this.thanosPosition = thanosPosition;
        this.stonePositions = stonePositions;
        this.warriorPositions = warriorPositions;
    }

    public boolean valid(int... params) {
        int m = params[0], n = params[1];

        if (this.equals(toEmptyState()))
            return false;
        if (this.ironManPosition() == null)
            return false;
        if (!this.ironManPosition().inBounds(m, n))
            return false;

        return true;
    }

    public boolean goal() {
        return snapped;
    }

    public boolean canSnap() {
        return stonePositions().size() == 0 && warriorPositions().size() == 0;
    }

    public boolean snapped() {
        return snapped;
    }

    public Cell ironManPosition() {
        return ironManPosition;
    }

    public Cell thanosPosition() {
        return thanosPosition;
    }

    public HashSet<Cell> stonePositions() {
        return stonePositions;
    }

    public HashSet<Cell> warriorPositions() {
        return warriorPositions;
    }

    @Override
    public int calculateStateCost(int... params) {
        int m = params[0], n = params[1];
        var cur = this.ironManPosition();

        int totalCost = 0;

        for (int[] dir : dirs) {
            Cell adjCell = new Cell(cur.x + dir[0], cur.y + dir[1]);

            if (!adjCell.inBounds(m, n))
                continue;

            if (this.warriorPositions().contains(adjCell)) {
                totalCost += 1;
            }

            if (this.thanosPosition().equals(adjCell)) {
                totalCost += 5;
            }
        }

        return totalCost;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null)
            return false;
        if (this.getClass() != other.getClass())
            return false;

        EndGameState otherState = (EndGameState) other;
        return (this.ironManPosition() != null && this.ironManPosition().equals(otherState.ironManPosition())
        // && this.stonePositions().equals(otherState.stonePositions())
        // && this.warriorPositions().equals(otherState.warriorPositions())
                && this.snapped() == otherState.snapped());
    }

    @Override
    @SuppressWarnings("unchecked")
    public EndGameState clone() {
        var clonedIronManPosition = new Cell(ironManPosition().x, ironManPosition().y);
        var clonedThanosPosition = new Cell(thanosPosition().x, thanosPosition().y);
        var clonedStonePositions = (HashSet<Cell>) stonePositions().clone();
        var clonedWarriorPositions = (HashSet<Cell>) warriorPositions().clone();

        return new EndGameState(snapped(), clonedIronManPosition, clonedThanosPosition, clonedStonePositions,
                clonedWarriorPositions);
    }

    @Override
    public EndGameState toEmptyState() {
        return new EndGameState(snapped(), null, null, null, null);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + ((this.ironManPosition() == null) ? 0 : this.ironManPosition().hashCode());
        // result = prime * result + ((this.snapped() == true) ? 0 : 1);
        result = prime * result + ((this.stonePositions() == null || this.stonePositions.size() == 0) ? 0
                : this.stonePositions().hashCode());
        result = prime * result + ((this.warriorPositions() == null || this.warriorPositions().size() == 0) ? 0
                : this.warriorPositions().hashCode());

        return result;
    }
}
