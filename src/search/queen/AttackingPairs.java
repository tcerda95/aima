package search.queen;

import search.framework.Heuristic;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class AttackingPairs implements Heuristic<NQueenBoard> {
    private final int n;
    private final int[] occupied;

    public AttackingPairs(final int n) {
        this.n = n;
        this.occupied = new int[n * 2 - 1];
    }

    @Override
    public int cost(final NQueenBoard board) {
        return rowPairs(board) + diagonalPairs(board);
    }

    private int rowPairs(final NQueenBoard board) {
        final int dim = board.getDimension();
        return pairs(dim, board::getQueen);
    }

    private int diagonalPairs(final NQueenBoard board) {
        return lowDiagonalPairs(board) + supDiagonalPairs(board);
    }

    private int lowDiagonalPairs(final NQueenBoard board) {
        return pairs(n * 2 - 1, i -> (board.getQueen(i) - i) + n - 1);
    }

    private int supDiagonalPairs(final NQueenBoard board) {
        return pairs(n * 2 - 1, i -> board.getQueen(i) + i);
    }

    private int pairs(final int size, final IntUnaryOperator positionMapper) {
        final int[] occupied = occupiedCount(size, positionMapper);
        final int pairs = pairs(occupied, size);
        Arrays.fill(occupied, 0);

        return pairs;
    }

    private int[] occupiedCount(final int size, final IntUnaryOperator positionMapper) {
        for (int i = 0; i < n; i++) {
            final int position = positionMapper.applyAsInt(i);
            occupied[position] += 1;
        }

        return occupied;
    }

    private int pairs(final int[] occupied, final int size) {
        int count = 0;

        for (int i = 0; i < size; i++)
            count += occupied[i] * (occupied[i] - 1) / 2;

        return count;
    }
}
