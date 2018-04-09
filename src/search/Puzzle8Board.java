package search;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Puzzle8Board {
    public static final int DIM = 3;
    public static final int EMPTY = 0;

    private final int[] board;
    private final int emptyCell;

    public Puzzle8Board(final int[][] board) {
        Objects.requireNonNull(board);

        this.board = new int[DIM * DIM];

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                final int index = coordinateToIndex(i, j);
                this.board[index] = board[i][j];
            }
        }

        this.emptyCell = findEmptyCell();
    }

    private Puzzle8Board(final int[] board) {
        this.board = Objects.requireNonNull(board);
        this.emptyCell = findEmptyCell();
    }

    public int[][] getBoard() {
        int[][] board = new int[DIM][DIM];

        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++)
                board[i][j] = getElement(i, j);

        return board;
    }

    public int getElement(final int row, final int column) {
        if (!isValidPosition(row, column))
            throw new IllegalStateException("Invalid position");

        final int index = coordinateToIndex(row, column);
        return board[index];
    }

    public Puzzle8Board applyMovement(final int rows, final int columns) {
        if (!isValidMovement(rows, columns))
            throw new IllegalArgumentException();

        final int row = emptyCell / DIM + rows;
        final int column = emptyCell % DIM + columns;
        final int element = getElement(row, column);
        final int[] newBoard = Arrays.copyOf(board, board.length);

        newBoard[emptyCell] = element;
        newBoard[coordinateToIndex(row, column)] = EMPTY;

        return new Puzzle8Board(newBoard);
    }

    public boolean isValidMovement(final int rows, final int columns) {
        final int row = emptyCell / DIM + rows;
        final int column = emptyCell % DIM + columns;
        return isValidPosition(row, column);
    }

    public boolean isValidPosition(final int row, final int column) {
        return isValidPosition(column) && isValidPosition(row);
    }

    public boolean isEmpty(final int row, final int column) {
        return isEmpty(coordinateToIndex(row, column));
    }

    private boolean isValidPosition(final int position) {
        return position >= 0 && position < DIM;
    }

    private int findEmptyCell() {
        for (int i = 0; i < board.length; i++)
            if (isEmpty(i))
                return i;

        throw new NoSuchElementException();
    }

    private boolean isEmpty(final int index) {
        return board[index] == EMPTY;
    }

    private int coordinateToIndex(final int row, final int column) {
        return column + row * DIM;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Puzzle8Board))
            return false;

        final Puzzle8Board other = (Puzzle8Board) obj;

        return Arrays.equals(board, other.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++)
                stringBuilder.append(getElement(i, j));

            stringBuilder.append('\n');
        }

        return stringBuilder.toString();
    }
}
