package endgame;

public class Cell implements Comparable {
    public int x, y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean inBounds(int m, int n) {
        return x >= 0 && x < m && y >= 0 && y < n;
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
        if (this == other) return true;
        if (other == null) return false;
        if (this.getClass() != other.getClass()) return false;

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
            return this.y - otherCell.y;
        }

        return this.x - otherCell.x;
    }

    @Override
    public int hashCode() {
        int prime = 37;
        int result = 1;

        result = prime * result + this.x;
        result = prime * result + this.y;

        return result;
    }
}
