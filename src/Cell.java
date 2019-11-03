package endgame;

public class Cell implements Comparable {
    public int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell incrementXBy(int increment) {
        this.x += increment;

        return this;
    }

    public Cell incrementYBy(int increment) {
        this.y += increment;

        return this;
    }

    public Cell clone() {
        return new Cell(this.x, this.y);
    }

    public String toString() {
        return this.x + "," + this.y;
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

    @Override
    public int compareTo(Object other) {
        if (this == other) {
            return 0;
        }

        Cell otherCell = (Cell) other;
        if (this.x == otherCell.x) {
            return new Integer (this.y).compareTo(otherCell.y);
        }

        return new Integer (this.x).compareTo(otherCell.x);
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;

        return hash;
    }
}
