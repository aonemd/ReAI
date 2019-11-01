package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

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

        // up
        targetCell = new Cell(ironManPosition.x, ironManPosition.y-1);
        if (targetCell.y >= 0 && !thanosPosition.equals(targetCell) && !warriorPositions.contains(targetCell)) {
            validOperators.add(this.operators.get("up"));
        }
        // down
        targetCell = new Cell(ironManPosition.x, ironManPosition.y+1);
        if (targetCell.y < gridHeight && !thanosPosition.equals(targetCell) && !warriorPositions.contains(targetCell)) {
            validOperators.add(this.operators.get("down"));
        }
        // right
        targetCell = new Cell(ironManPosition.x+1, ironManPosition.y);
        if (targetCell.x < gridWidth && !thanosPosition.equals(targetCell) && !warriorPositions.contains(targetCell)) {
            validOperators.add(this.operators.get("right"));
        }
        // left
        targetCell = new Cell(ironManPosition.x-1, ironManPosition.y);
        if (targetCell.x >= 0 && !thanosPosition.equals(targetCell) && !warriorPositions.contains(targetCell)) {
            validOperators.add(this.operators.get("left"));
        }
        // collect
        targetCell = ironManPosition;
        if (stonePositions.contains(targetCell)) {
            validOperators.add(this.operators.get("collect"));
        }
        // kill
        List<Cell> adjacentCells = adjacentCells(this.ironManPosition);
        // kill if the two lists have elements in common
        if (!Collections.disjoint(this.warriorPositions, adjacentCells)) {
            validOperators.add(this.operators.get("kill"));
        }
        // snap
        if (this.stonePositions.size() == 0 &&
                this.ironManPosition.equals(this.thanosPosition) &&
                this.ironManDamage < 100) {
            validOperators.add(this.operators.get("snap"));
        }

        return validOperators;
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

    public EndGameState applyOperator(String operatorName) {
        Method method;

        try {
            method = this.getClass().getDeclaredMethod(operatorName);
            method.setAccessible(true);

            return (EndGameState) method.invoke(this);
        } catch (SecurityException
                | NoSuchMethodException
                | IllegalArgumentException
                | IllegalAccessException
                | InvocationTargetException e) {
            System.out.println("Error:" + e);
        }

        return this;
    }

    public boolean isGoal() {
        return this.snapped;
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

    private EndGameState up() {
        this.ironManPosition.incrementYBy(-1);

        return this;
    }

    private EndGameState down() {
        this.ironManPosition.incrementYBy(1);

        return this;
    }

    private EndGameState right() {
        this.ironManPosition.incrementXBy(1);

        return this;
    }

    private EndGameState left() {
        this.ironManPosition.incrementXBy(-1);

        return this;
    }

    private EndGameState collect() {
        this.stonePositions.remove(this.ironManPosition);
        this.ironManDamage += 3;

        return this;
    }

    private EndGameState kill() {
        List<Cell> adjacentCells = adjacentCells(this.ironManPosition);

        for (Cell adjacentCell : adjacentCells) {
            if (this.warriorPositions.contains(adjacentCell)) {
                this.warriorPositions.remove(adjacentCell);
                this.ironManDamage += 2;
            }
        }

        return this;
    }

    private EndGameState snap() {
        this.snapped = true;

        return this;
    }

    private List<Cell> adjacentCells(Cell centerCell) {
        List<Cell> cells = new ArrayList<Cell>();
        cells.add(centerCell.clone().incrementYBy(-1));
        cells.add(centerCell.clone().incrementYBy(1));
        cells.add(centerCell.clone().incrementXBy(1));
        cells.add(centerCell.clone().incrementXBy(-1));

        return cells;
    }
}
