package search.algorithm;

import search.framework.Node;
import search.framework.Problem;
import search.puzzle.AdyacentMovement;
import search.puzzle.PuzzleNBoard;
import search.puzzle.PuzzleNProblem;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch<S,A> extends GraphSearch<S,A> {
    private final Queue<Node<S,A>> queue = new LinkedList<>();

    public BreadthFirstSearch(final Problem<S,A> problem) {
        super(problem, true);
    }

    @Override
    protected void addToFrontier(final Node<S,A> node) {
        queue.add(node);
    }

    @Override
    protected Node<S,A> removeFromFrontier() {
        return queue.remove();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return queue.isEmpty();
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 12, PuzzleNBoard.EMPTY, 10},
                {6, 11, 8, 15},
                {13, 3, 2, 14},
                {1, 4, 9, 7}
        };
        final Problem<PuzzleNBoard, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final BreadthFirstSearch<PuzzleNBoard, AdyacentMovement> bfs = new BreadthFirstSearch<>(puzzle8Problem);
        final Node<PuzzleNBoard, AdyacentMovement> ans = bfs.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
        System.out.println("Metrics: " + bfs.getMetrics());
    }
}
