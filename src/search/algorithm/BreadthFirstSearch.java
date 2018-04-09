package search.algorithm;

import search.*;
import search.framework.Node;
import search.framework.Problem;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Optional;

public class BreadthFirstSearch<S,A> {
    private final Problem<S,A> problem;

    public BreadthFirstSearch(final Problem<S,A> problem) {
        this.problem = problem;
    }

    public Optional<Node<S,A>> search() {
        final Queue<Node<S,A>> frontier = new LinkedList<>();
        final Set<S> exploredSet = new HashSet<>();
        final Node<S,A> root = Node.rootNode(problem.getInitialState());

        if (problem.isGoal(root))
            return Optional.of(root);

        frontier.offer(root);

        while (!frontier.isEmpty()) {
            final Node<S,A> node = frontier.poll();
            final S state = node.getState();

            if (!exploredSet.contains(state)) {
                exploredSet.add(state);

                for (final A action : problem.getActions(node)) {
                    final Node<S,A> childNode = Node.childNode(problem, node, action);
                    final S childState = childNode.getState();

                    if (!exploredSet.contains(childState)) {
                        if (problem.isGoal(childState))
                            return Optional.of(childNode);

                        frontier.offer(childNode);
                    }
                }
            }
        }

        return Optional.empty();
    }

    public static final void main(final String[] args) {
        int[][] board = new int[][] {
                {5, 4, Puzzle8Board.EMPTY},
                {6, 1, 8},
                {7, 3, 2}
        };
        final Problem<Puzzle8Board, AdyacentMovement> puzzle8Problem = new PuzzleNProblem(board);
        final BreadthFirstSearch<Puzzle8Board, AdyacentMovement> bfs = new BreadthFirstSearch<>(puzzle8Problem);
        final Node<Puzzle8Board, AdyacentMovement> ans = bfs.search().get();
        System.out.println(ans);
        System.out.println(ans.getPathCost());
    }
}
