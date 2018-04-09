package search.algorithm;

import search.framework.Node;
import search.framework.Problem;

import java.util.*;

public abstract class GraphSearch<S,A> {
    private final Problem<S,A> problem;
    private final boolean testGoalEarly;
    private int iterations;
    private Metrics metrics;

    public GraphSearch(final Problem<S,A> problem) {
        this(problem, false);
    }

    public GraphSearch(final Problem<S,A> problem, final boolean testGoalEarly) {
        this.problem = Objects.requireNonNull(problem);
        this.testGoalEarly = testGoalEarly;
    }

    protected abstract void addToFrontier(final Node<S,A> node);
    protected abstract Node<S,A> removeFromFrontier();
    protected abstract boolean isFrontierEmpty();

    public Optional<Node<S,A>> search() {
        final long startTime = System.currentTimeMillis();
        final Optional<Node<S,A>> result = searchNode();
        final long endTime = System.currentTimeMillis();
        metrics = new Metrics(endTime - startTime, iterations);
        return result;
    }

    private Optional<Node<S,A>> searchNode() {
        iterations = 0;
        final Set<S> exploredSet = new HashSet<>();
        final Node<S,A> root = Node.rootNode(problem.getInitialState());

        if (testGoalEarly && problem.isGoal(root))
            return Optional.of(root);

        addToFrontier(root);

        while (!isFrontierEmpty()) {
            final Node<S,A> node = removeFromFrontier();
            final S state = node.getState();

            if (!exploredSet.contains(state)) {
                iterations += 1;

                if (!testGoalEarly && problem.isGoal(node))
                    return Optional.of(node);

                exploredSet.add(state);

                for (final A action : problem.getActions(node)) {
                    final Node<S,A> childNode = Node.childNode(problem, node, action);
                    final S childState = childNode.getState();

                    if (!exploredSet.contains(childState)) {
                        if (testGoalEarly && problem.isGoal(childState))
                            return Optional.of(childNode);

                        addToFrontier(childNode);
                    }
                }
            }
        }

        return Optional.empty();
    }

    public Metrics getMetrics() {
        return metrics;
    }

}
