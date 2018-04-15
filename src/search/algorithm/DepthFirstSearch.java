package search.algorithm;

import search.puzzle.AdyacentMovement;
import search.puzzle.PuzzleNBoard;
import search.puzzle.PuzzleNProblem;
import search.framework.Node;
import search.framework.Problem;

import java.util.Deque;
import java.util.LinkedList;

public class DepthFirstSearch<S,A> extends GraphSearch<S,A> {
    private final Deque<Node<S,A>> stack;

    public DepthFirstSearch(final Problem<S,A> problem) {
        super(problem);
        stack = new LinkedList<>();
    }

    @Override
    protected void addToFrontier(final Node<S,A> node) {
        stack.push(node);
    }

    @Override
    protected Node<S,A> removeFromFrontier() {
        return stack.pop();
    }

    @Override
    protected boolean isFrontierEmpty() {
        return stack.isEmpty();
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 4, PuzzleNBoard.EMPTY},
                {6, 1, 8},
                {7, 3, 2}
        };
        final Problem<PuzzleNBoard, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final DepthFirstSearch<PuzzleNBoard, AdyacentMovement> dfs = new DepthFirstSearch<>(puzzle8Problem);
        final Node<PuzzleNBoard, AdyacentMovement> ans = dfs.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
    }
}
