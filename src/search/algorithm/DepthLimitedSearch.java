package search.algorithm;

import search.puzzle.AdyacentMovement;
import search.puzzle.PuzzleNBoard;
import search.puzzle.PuzzleNProblem;
import search.framework.Node;
import search.framework.Problem;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

public class DepthLimitedSearch<S,A> {
    private final Problem<S,A> problem;
    private int iterations;
    private Metrics metrics;

    public DepthLimitedSearch(final Problem<S,A> problem) {
        this.problem = Objects.requireNonNull(problem);
    }

    public Optional<Node<S,A>> search(final int limit) {
        if (limit < 0)
            throw new IllegalArgumentException("Limit must be non negative");

        iterations = 0;

        final long startTime = System.currentTimeMillis();
        final Optional<Node<S,A>> result = search(Node.rootNode(problem.getInitialState()), limit, new HashSet<S>());
        final long endTime = System.currentTimeMillis();

        metrics = new Metrics(endTime - startTime, iterations);

        return result;
    }

    private Optional<Node<S,A>> search(final Node<S,A> node, final int limit, final HashSet<S> exploredBranch) {
        iterations += 1;

        if (problem.isGoal(node))
            return Optional.of(node);

        if (limit == 0)
            return Optional.empty();

        final S state = node.getState();
        exploredBranch.add(state);

        for (final A action : problem.getActions(node)) {
            final Node<S,A> childNode = Node.childNode(problem, node, action);

            if (!exploredBranch.contains(childNode.getState())) {
                final Optional<Node<S,A>> result = search(childNode, limit - 1, exploredBranch);
                if (result.isPresent())
                    return result;
            }
        }

        exploredBranch.remove(state);
        return Optional.empty();
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 4, PuzzleNBoard.EMPTY},
                {6, 1, 8},
                {7, 3, 2}
        };
        final Problem<PuzzleNBoard, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final DepthLimitedSearch<PuzzleNBoard, AdyacentMovement> dfs = new DepthLimitedSearch<>(puzzle8Problem);
        final Node<PuzzleNBoard, AdyacentMovement> ans = dfs.search(24).get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
        System.out.println("Metrics: " + dfs.getMetrics());
    }
}
