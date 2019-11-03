package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

import static util.Invoker.invoke;

import search.State;
import search.Operator;

public class EndGameState implements State {
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private Set<Cell> stonePositions;
    private Set<Cell> warriorPositions;
    private HashMap<String, Operator> operators;
    private int ironManDamage;
    private boolean snapped;

    public EndGameState(int gridWidth,
                        int gridHeight,
                        Cell ironManPosition,
                        Cell thanosPosition,
                        Set<Cell> stonePositions,
                        Set<Cell> warriorPositions,
                        HashMap<String, Operator> operators,
                        int ironManDamage,
                        boolean snapped) {
        this.gridWidth        = gridWidth;
        this.gridHeight       = gridHeight;
        this.ironManPosition  = ironManPosition;
        this.thanosPosition   = thanosPosition;
        this.stonePositions   = stonePositions;
        this.warriorPositions = warriorPositions;
        this.operators        = operators;
        this.ironManDamage    = ironManDamage;
        this.snapped          = snapped;
    }

    public List<Operator> validOperators() {
        List<Operator> validOperators = new ArrayList<Operator>();

        Cell targetCell;

        // snap
        if (this.stonePositions.size() == 0 &&
                this.ironManPosition.equals(this.thanosPosition) &&
                this.ironManDamage < 100) {
            validOperators.add(this.operators.get("snap"));
        }
        // collect
        targetCell = ironManPosition;
        if (stonePositions.contains(targetCell)) {
            validOperators.add(this.operators.get("collect"));
        }
        // kill
        Set<Cell> adjacentCells = adjacentCells(this.ironManPosition);
        // kill if the two sets intersect
        if (new HashSet<Cell>(this.warriorPositions).removeAll(adjacentCells)) {
            validOperators.add(this.operators.get("kill"));
        }
        // up
        targetCell = this.ironManPosition.clone().incrementXBy(-1);
        if (targetCell.x >= 0 && canIronManEnterCell(targetCell)) {
            validOperators.add(this.operators.get("up"));
        }
        // down
        targetCell = this.ironManPosition.clone().incrementXBy(1);
        if (targetCell.x < gridHeight && canIronManEnterCell(targetCell)) {
            validOperators.add(this.operators.get("down"));
        }
        // right
        targetCell = this.ironManPosition.clone().incrementYBy(1);
        if (targetCell.y < gridWidth && canIronManEnterCell(targetCell)) {
            validOperators.add(this.operators.get("right"));
        }
        // left
        targetCell = this.ironManPosition.clone().incrementYBy(-1);
        if (targetCell.y >= 0 && canIronManEnterCell(targetCell)) {
            validOperators.add(this.operators.get("left"));
        }

        return validOperators;
    }

    public EndGameState applyOperator(String operatorName) {
        EndGameState invokedState = (EndGameState) invoke(this, operatorName);

        if (invokedState.isAdjacentToThanos()) {
            invokedState.ironManDamage += 5;
        }

        invokedState.ironManDamage += invokedState.getAdjacentWarriorCount() * 1;

        return invokedState;
    }

    public boolean isGoal() {
        return this.snapped;
    }

    public boolean isDead() {
        return (this.getCost() >= 100);
    }

    public int getCost() {
        return this.ironManDamage;
    }

    public double hfI() {
        return (Math.abs(this.thanosPosition.x - this.ironManPosition.x)
                + Math.abs(this.thanosPosition.y - this.ironManPosition.y));
    }

    public String toString() {
        String hashedStoneCells = String.join(",", this.stonePositions.stream().map(Cell::toString).collect(Collectors.toList()));
        String hashedwarriorCells = String.join(",", this.warriorPositions.stream().map(Cell::toString).collect(Collectors.toList()));

        return this.ironManPosition.toString()
            + ";"
            + hashedStoneCells
            + ";"
            + hashedwarriorCells
            + ";"
            + this.ironManDamage;
    }

    public EndGameState clone() {
        Set<Cell> clonedStonePositions = new HashSet<Cell>();
        for (Cell stoneCell : this.stonePositions) {
            clonedStonePositions.add(stoneCell.clone());
        }

        Set<Cell> clonedWarriorPositions = new HashSet<Cell>();
        for (Cell warriorCell : this.warriorPositions) {
            clonedWarriorPositions.add(warriorCell.clone());
        }

        return new EndGameState(this.gridWidth,
                                this.gridHeight,
                                this.ironManPosition.clone(),
                                this.thanosPosition.clone(),
                                clonedStonePositions,
                                clonedWarriorPositions,
                                this.operators,
                                this.ironManDamage,
                                this.snapped);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        EndGameState otherState = (EndGameState) other;
        return (this.ironManPosition.equals(otherState.ironManPosition)
                && this.stonePositions.equals(otherState.stonePositions)
                && this.warriorPositions.equals(otherState.warriorPositions)
                && this.getCost() == otherState.getCost());
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = prime * result + ((this.ironManPosition == null) ? 0 : this.ironManPosition.hashCode());
        result = prime * result + ((this.stonePositions == null || this.stonePositions.size() == 0) ? 0 : this.stonePositions.hashCode());
        result = prime * result + ((this.warriorPositions == null || this.warriorPositions.size() == 0) ? 0 : this.warriorPositions.hashCode());
        result = prime * result + ((this.getCost() == 0) ? 0 : this.getCost());

        return result;
    }

    private EndGameState snap() {
        this.snapped = true;

        return this;
    }

    private EndGameState collect() {
        this.stonePositions.remove(this.ironManPosition);
        this.ironManDamage += this.operators.get("kill").cost;

        return this;
    }

    private EndGameState kill() {
        Set<Cell> adjacentCells = adjacentCells(this.ironManPosition);

        for (Cell adjacentCell : adjacentCells) {
            if (this.warriorPositions.contains(adjacentCell)) {
                this.warriorPositions.remove(adjacentCell);
                this.ironManDamage += this.operators.get("kill").cost;
            }
        }

        return this;
    }

    private EndGameState up() {
        this.ironManPosition.incrementXBy(-1);

        return this;
    }

    private EndGameState down() {
        this.ironManPosition.incrementXBy(1);

        return this;
    }

    private EndGameState right() {
        this.ironManPosition.incrementYBy(1);

        return this;
    }

    private EndGameState left() {
        this.ironManPosition.incrementYBy(-1);

        return this;
    }

    private boolean canIronManEnterCell(Cell targetCell) {
        return (!this.warriorPositions.contains(targetCell)
                && (!this.thanosPosition.equals(targetCell)
                    || (this.thanosPosition.equals(targetCell) && this.stonePositions.size() == 0)));
    }

    private boolean isAdjacentToThanos() {
        Set<Cell> adjacentCells = adjacentCells(this.ironManPosition);

        return adjacentCells.contains(this.thanosPosition);
    }

    private int getAdjacentWarriorCount() {
        int adjacentWarriorCount = 0;

        Set<Cell> adjacentCells = adjacentCells(this.ironManPosition);
        for (Cell adjacentCell : adjacentCells) {
            if (this.warriorPositions.contains(adjacentCell)) {
                adjacentWarriorCount++;
            }
        }

        return adjacentWarriorCount;
    }

    private Set<Cell> adjacentCells(Cell centerCell) {
        Set<Cell> cells = new HashSet<Cell>();
        cells.add(centerCell.clone().incrementXBy(-1));
        cells.add(centerCell.clone().incrementXBy(1));
        cells.add(centerCell.clone().incrementYBy(1));
        cells.add(centerCell.clone().incrementYBy(-1));

        return cells;
    }
}
