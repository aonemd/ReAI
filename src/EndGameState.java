package endgame;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

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
}
