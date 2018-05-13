package search.local.hclimbing;

import search.framework.Heuristic;
import search.framework.Objective;
import search.framework.ObjectiveNode;
import search.framework.Problem;
import search.queen.AttackingPairs;
import search.queen.NQueenBoard;
import search.queen.NQueenMovement;
import search.queen.NQueenProblem;

import java.util.Collections;
import java.util.List;

public class SteepestAscent<S,A> extends HillClimbing<S,A> {
    protected ObjectiveNode<S> findSuccessor(final ObjectiveNode<S> node, final Problem<S,A> problem, final Objective<S> objective) {
        final List<A> actions = problem.getActions(node.getState());
        int maxValue = Integer.MIN_VALUE;
        ObjectiveNode<S> maxNode = null;
        Collections.shuffle(actions); // random maximal node should be chosen

        for (final A action : actions) {
            final S neighborState = problem.result(node.getState(), action);
            final int value = objective.value(neighborState);

            if (value > maxValue) {
                maxValue = value;
                maxNode = new ObjectiveNode<>(neighborState, value);
            }
        }

        return maxNode;
    }

    public static void main(String[] args) {
        final int n = 8;
        final Problem<NQueenBoard, NQueenMovement> problem = new NQueenProblem(n);
        final HillClimbing<NQueenBoard, NQueenMovement> hc = new SteepestAscent<>();
        final Heuristic<NQueenBoard> heuristic = new AttackingPairs(n);
        final ObjectiveNode<NQueenBoard> node = hc.searchMin(problem, heuristic);

        System.out.println(node);
        System.out.println(node.getCost());
        System.out.println(problem.isGoal(node.getState()));
    }
}
