package search;

import search.framework.Problem;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PuzzleNProblem implements Problem<Puzzle8Board, AdyacentMovement> {
    private final Puzzle8Board initialState;

    public PuzzleNProblem(final int[][] initialState) {
        this.initialState = new Puzzle8Board(Objects.requireNonNull(initialState));
    }

    @Override
    public Puzzle8Board getInitialState() {
        return initialState;
    }

    @Override
    public List<AdyacentMovement> getActions(final Puzzle8Board state) {
        final List<AdyacentMovement> actions = new LinkedList<>();

        for (final AdyacentMovement movement : AdyacentMovement.values())
            if (state.isValidMovement(movement.getRows(), movement.getColumns()))
                actions.add(movement);

        return actions;
    }

    @Override
    public Puzzle8Board result(final Puzzle8Board state, final AdyacentMovement action) {
        return state.applyMovement(action.getRows(), action.getColumns());
    }

    @Override
    public int stepCost(final Puzzle8Board from, final AdyacentMovement action, final Puzzle8Board to) {
        return 1;
    }

    @Override
    public boolean isGoal(final Puzzle8Board state) {
        final int[][] board = state.getBoard();
        int prev = -1;

        for (int i = 0; i < Puzzle8Board.DIM; i++) {
            for (int j = 0; j < Puzzle8Board.DIM; j++) {
                if (board[i][j] <= prev)
                    return false;

                prev = board[i][j];
            }
        }

        return true;
    }
}
