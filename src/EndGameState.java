package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
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

    public EndGameState(int gridWidth,
                        int gridHeight,
                        Cell ironManPosition,
                        Cell thanosPosition,
                        List<Cell> stonePositions,
                        List<Cell> warriorPositions,
                        HashMap<String, Operator> operators,
                        int ironManDamage) {
        this.gridWidth        = gridWidth;
        this.gridHeight       = gridHeight;
        this.ironManPosition  = ironManPosition;
        this.thanosPosition   = thanosPosition;
        this.stonePositions   = stonePositions;
        this.warriorPositions = warriorPositions;
        this.operators        = operators;
        this.ironManDamage    = ironManDamage;
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
        validOperators.add(this.operators.get("kill"));
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
                                this.ironManDamage);
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
        Cell[] adjacentCells = new Cell[] {
            this.ironManPosition.clone().incrementYBy(-1),
            this.ironManPosition.clone().incrementYBy(1),
            this.ironManPosition.clone().incrementXBy(1),
            this.ironManPosition.clone().incrementXBy(-1)
        };

        for (Cell adjacentCell : adjacentCells) {
            if (this.warriorPositions.contains(adjacentCell)) {
                this.warriorPositions.remove(adjacentCell);
                this.ironManDamage += 2;
            }
        }

        return this;
    }

    private EndGameState snap() {
        return this;
    }
}
