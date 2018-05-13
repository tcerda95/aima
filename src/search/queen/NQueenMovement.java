package search.queen;

import java.util.Objects;

/**
 * Moves a given queen to a given row
 */
public class NQueenMovement {
    private final int queen;
    private final int row;

    public NQueenMovement(final int queen, final int row) {
        this.queen = queen;
        this.row = row;
    }

    public int getQueen() {
        return queen;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof NQueenMovement))
            return false;

        final NQueenMovement other = (NQueenMovement) obj;

        return queen == other.queen && row == other.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(queen, row);
    }

    @Override
    public String toString() {
        return queen + " -> " + row;
    }
}
