package search.algorithm;

import search.puzzle.AdyacentMovement;
import search.puzzle.PuzzleNBoard;
import search.puzzle.PuzzleNProblem;
import search.framework.Node;
import search.framework.Problem;

import java.util.Comparator;
import java.util.PriorityQueue;

public class UniformCostSearch<S,A> extends GraphSearch<S,A> {
    private static final int INIT_SIZE = 1024;

    private final PriorityQueue<Node<S,A>> pq = new PriorityQueue<>(INIT_SIZE, Comparator.comparingInt(Node::getPathCost));

    public UniformCostSearch(final Problem<S,A> problem) {
        super(problem);
    }

    @Override
    protected void addToFrontier(final Node node) {
        pq.add(node);
    }

    @Override
    protected Node removeFromFrontier() {
        return pq.remove();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return pq.isEmpty();
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, PuzzleNBoard.EMPTY}
        };
        final Problem<PuzzleNBoard, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final UniformCostSearch<PuzzleNBoard, AdyacentMovement> ucs = new UniformCostSearch<>(puzzle8Problem);
        final Node<PuzzleNBoard, AdyacentMovement> ans = ucs.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
        System.out.println("Metrics: " + ucs.getMetrics());
    }
}
