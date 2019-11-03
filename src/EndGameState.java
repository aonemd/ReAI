package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;

import static util.Invoker.invoke;

import search.State;
import search.Operator;

public class EndGameState implements State {
    private int gridWidth;
    private int gridHeight;
    private Cell ironManPosition;
    private Cell thanosPosition;
    private List<Cell> stonePositions;
    private List<Cell> warriorPositions;
    private HashMap<String, Operator> operators;
    private int ironManDamage;
    private boolean snapped;

    public EndGameState(int gridWidth,
                        int gridHeight,
                        Cell ironManPosition,
                        Cell thanosPosition,
                        List<Cell> stonePositions,
                        List<Cell> warriorPositions,
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
        HashSet<Cell> adjacentCells = adjacentCells(this.ironManPosition);
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

    public String toString() {
        this.stonePositions.sort(Comparator.naturalOrder());
        this.warriorPositions.sort(Comparator.naturalOrder());

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
        List<Cell> clonedStonePositions = new ArrayList<Cell>();
        for (Cell stoneCell : this.stonePositions) {
            clonedStonePositions.add(stoneCell.clone());
        }

        List<Cell> clonedWarriorPositions = new ArrayList<Cell>();
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
                && this.ironManDamage == otherState.ironManDamage
               );
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
        HashSet<Cell> adjacentCells = adjacentCells(this.ironManPosition);

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
        HashSet<Cell> adjacentCells = adjacentCells(this.ironManPosition);

        return adjacentCells.contains(this.thanosPosition);
    }

    private int getAdjacentWarriorCount() {
        int adjacentWarriorCount = 0;

        HashSet<Cell> adjacentCells = adjacentCells(this.ironManPosition);
        for (Cell adjacentCell : adjacentCells) {
            if (this.warriorPositions.contains(adjacentCell)) {
                adjacentWarriorCount++;
            }
        }

        return adjacentWarriorCount;
    }

    private HashSet<Cell> adjacentCells(Cell centerCell) {
        HashSet<Cell> cells = new HashSet<Cell>();
        cells.add(centerCell.clone().incrementXBy(-1));
        cells.add(centerCell.clone().incrementXBy(1));
        cells.add(centerCell.clone().incrementYBy(1));
        cells.add(centerCell.clone().incrementYBy(-1));

        return cells;
    }
}
