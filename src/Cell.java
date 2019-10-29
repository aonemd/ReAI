package endgame;

public class Cell {
    public int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (this.getClass() != other.getClass()) {
            return false;
        }

        Cell otherCell = (Cell) other;
        return this.x == otherCell.x && this.y == otherCell.y;
    }
}
