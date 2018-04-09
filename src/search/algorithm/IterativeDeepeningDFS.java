package search.algorithm;

import search.AdyacentMovement;
import search.Puzzle8Board;
import search.PuzzleNProblem;
import search.framework.Node;
import search.framework.Problem;

import java.util.Objects;
import java.util.Optional;

public class IterativeDeepeningDFS<S,A> {
    private final Problem<S,A> problem;
    private final DepthLimitedSearch<S,A> depthLimitedSearch;
    private Metrics metrics;

    public IterativeDeepeningDFS(final Problem<S,A> problem) {
        this.problem = Objects.requireNonNull(problem);
        this.depthLimitedSearch = new DepthLimitedSearch<>(this.problem);
    }

    public Optional<Node<S,A>> search() {
        long timeElapsed = 0;
        int iterations = 0;
        Optional<Node<S,A>> result = Optional.empty();

        for (int limit = 0; !result.isPresent(); limit++) {
            result = depthLimitedSearch.search(limit++);

            final Metrics resultMetrics = depthLimitedSearch.getMetrics();
            timeElapsed += resultMetrics.getTimeElapsed();
            iterations += resultMetrics.getIterations();
        }

        metrics = new Metrics(timeElapsed, iterations);
        return result;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 4, Puzzle8Board.EMPTY},
                {6, 1, 8},
                {7, 3, 2}
        };

        final Problem<Puzzle8Board, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final IterativeDeepeningDFS<Puzzle8Board, AdyacentMovement> dfs = new IterativeDeepeningDFS<>(puzzle8Problem);
        final Node<Puzzle8Board, AdyacentMovement> ans = dfs.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
        System.out.println("Metrics: " + dfs.getMetrics());
    }
}
