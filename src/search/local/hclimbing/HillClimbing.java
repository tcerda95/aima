package search.local.hclimbing;

import search.framework.Heuristic;
import search.framework.Objective;
import search.framework.ObjectiveNode;
import search.framework.Problem;
import search.queen.AttackingPairs;
import search.queen.NQueenBoard;
import search.queen.NQueenMovement;
import search.queen.NQueenProblem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HillClimbing<S,A> {
    private Objective<S> objective;

    public ObjectiveNode<S> searchMax(final Problem<S,A> problem, final S initialState, final Objective<S> objective) {
        this.objective = objective;

        ObjectiveNode<S> node = buildNode(initialState);
        ObjectiveNode<S> successor = findSuccessor(node, problem, objective);

        while (shouldSearch(node, successor)) {
            node = successor;
            successor = findSuccessor(node, problem, objective);
        }

        return node;
    }

    public ObjectiveNode<S> searchMax(final Problem<S,A> problem, final Objective<S> objective) {
        return searchMax(problem, problem.getInitialState(), objective);
    }

    public ObjectiveNode<S> searchMin(final Problem<S,A> problem, final S initialState, final Heuristic<S> heuristic) {
        return searchMax(problem, initialState, s -> -heuristic.cost(s));
    }

    public ObjectiveNode<S> searchMin(final Problem<S,A> problem, final Heuristic<S> heuristic) {
        return searchMin(problem, problem.getInitialState(), heuristic);
    }

    private ObjectiveNode<S> buildNode(final S state) {
        return new ObjectiveNode<S>(state, objective.value(state));
    }

    protected abstract ObjectiveNode<S> findSuccessor(final ObjectiveNode<S> node, final Problem<S,A> problem, final Objective<S> objective);

    protected boolean shouldSearch(final ObjectiveNode<S> node, final ObjectiveNode<S> successor) {
        return successor.getValue() > node.getValue();
    }
}
