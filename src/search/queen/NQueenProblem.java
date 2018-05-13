package search.queen;

import search.framework.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;

public class NQueenProblem implements Problem<NQueenBoard, NQueenMovement> {
    private final int n;
    private final List<NQueenMovement> actions;

    public NQueenProblem(final int n) {
        this.n = n;
        this.actions = new ArrayList<>(n * n);
        populateActions();
    }

    private void populateActions() {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                actions.add(new NQueenMovement(i, j));
    }

    @Override
    public NQueenBoard getInitialState() {
        return new NQueenBoard(n);
    }

    @Override
    public List<NQueenMovement> getActions(final NQueenBoard board) {
        final List<NQueenMovement> availableActions = new ArrayList<>(actions);

        for (int i = 0; i < n; i++)
            availableActions.remove(i * n + board.getQueen(i) - i);

        return availableActions;
    }

    @Override
    public NQueenBoard result(final NQueenBoard state, final NQueenMovement action) {
        return state.applyMovement(action);
    }

    @Override
    public int stepCost(final NQueenBoard from, final NQueenMovement action, final NQueenBoard to) {
        return 1;
    }

    @Override
    public boolean isGoal(final NQueenBoard state) {
        return !hasRowRepetitions(state) && !hasDiagonalRepetitions(state);
    }

    private boolean hasRowRepetitions(final NQueenBoard board) {
        return hasRepetitions(n, board::getQueen);
    }

    private boolean hasDiagonalRepetitions(final NQueenBoard board) {
        return lowDiagonalRepetitions(board) || supDiagonalRepetitions(board);
    }

    private boolean lowDiagonalRepetitions(final NQueenBoard board) {
        return hasRepetitions(n * 2 - 1, i -> (board.getQueen(i) - i) + n - 1);
    }

    private boolean supDiagonalRepetitions(final NQueenBoard board) {
        return hasRepetitions(n * 2 - 1, i -> board.getQueen(i) + i);
    }

    private boolean hasRepetitions(final int repetitionSize, final IntUnaryOperator positionMapper) {
        boolean[] occupied = new boolean[repetitionSize];

        for (int i = 0; i < n; i++) {
            final int position = positionMapper.applyAsInt(i);

            if (occupied[position])
                return true;

            occupied[position] = true;
        }

        return false;
    }
}
