package search.algorithm;

import search.framework.Node;
import search.framework.Problem;
import search.puzzle.AdyacentMovement;
import search.puzzle.ManhattanDistance;
import search.puzzle.PuzzleNBoard;
import search.puzzle.PuzzleNProblem;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

public class AStarSearch<S,A> extends GraphSearch<S,A> {
    private static final int INIT_SIZE = 1024;

    private final PriorityQueue<Node<S,A>> pq;

    public AStarSearch(final Problem<S,A> problem, final ToIntFunction<S> heuristic) {
        super(problem);
        final Comparator<Node<S,A>> cmp = Comparator.comparingInt(n -> n.getPathCost() + heuristic.applyAsInt(n.getState()));
        pq = new PriorityQueue<>(INIT_SIZE, cmp);
    }

    @Override
    protected void addToFrontier(final Node<S, A> node) {
        pq.add(node);
    }

    @Override
    protected Node<S, A> removeFromFrontier() {
        return pq.remove();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return pq.isEmpty();
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 1, 2, 4},
                {14, 9, 3, 7},
                {13, 10, 12, 6},
                {15, 11, 8, PuzzleNBoard.EMPTY}
        };
        final Problem<PuzzleNBoard, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);

        final AStarSearch<PuzzleNBoard, AdyacentMovement> aStar = new AStarSearch<>(puzzle8Problem, ManhattanDistance.getInstance());
        final Node<PuzzleNBoard, AdyacentMovement> ans = aStar.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
        System.out.println("Metrics: " + aStar.getMetrics());
    }

}
