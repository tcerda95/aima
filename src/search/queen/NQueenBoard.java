package search.queen;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class NQueenBoard {
    private static final Random rand = new Random();

    // queens[i] yields the row of the ith queen
    private final int[] queens;

    public NQueenBoard(final int n) {
        queens = rand.ints(n, 0, n).toArray();
    }

    public NQueenBoard(final int[] queens) {
        this.queens = Objects.requireNonNull(queens);
    }

    public int getQueen(int n) {
        if (n < 0 || n >= queens.length)
            throw new IllegalArgumentException("Queen must be between 0 and N - 1. Received " + n);

        return queens[n];
    }

    public NQueenBoard applyMovement(final NQueenMovement movement) {
        final int[] ans = Arrays.copyOf(queens, queens.length);
        ans[movement.getQueen()] = movement.getRow();
        return new NQueenBoard(ans);
    }

    public int getDimension() {
        return queens.length;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof NQueenBoard))
            return false;

        final NQueenBoard other = (NQueenBoard) obj;

        return Arrays.equals(queens, other.queens);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(queens);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < queens.length; i++) {
            for (int j = 0; j < queens.length; j++) {
                builder.append('|');
                builder.append(queens[j] == i ? 'X' : ' ');
            }
            builder.append('|');
            builder.append('\n');
        }

        return builder.toString();
    }
}
