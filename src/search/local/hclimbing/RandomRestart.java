package search.local.hclimbing;

import search.framework.*;
import search.queen.AttackingPairs;
import search.queen.NQueenBoard;
import search.queen.NQueenMovement;
import search.queen.NQueenProblem;

public class RandomRestart<S,A> {
    private final HillClimbing<S,A> hc;

    public RandomRestart(final HillClimbing<S,A> hc) {
        this.hc = hc;
    }

    public ObjectiveNode<S> searchMax(final Problem<S,A> problem, final StateGenerator<S> stateGenerator, final Objective<S> objective, final int maxTries) {
        int tries = 0;
        ObjectiveNode<S> best = null;

        while (tries++ < maxTries) {
            final ObjectiveNode<S> node = hc.searchMax(problem, stateGenerator.nextState(), objective);

            if (best == null || node.getValue() > best.getValue())
                best = node;

            if (problem.isGoal(best.getState()))
                return best;
        }

        return best;
    }

    public ObjectiveNode<S> searchMin(final Problem<S,A> problem, final StateGenerator<S> stateGenerator, final Heuristic<S> heuristic, final int maxTries) {
        return searchMax(problem, stateGenerator, s -> -heuristic.cost(s), maxTries);
    }

    public static void main(String[] args) {
        final int n = 8;
        final Problem<NQueenBoard, NQueenMovement> problem = new NQueenProblem(n);
        final RandomRestart<NQueenBoard, NQueenMovement> hc = new RandomRestart<>(new SteepestAscent<>());
        final Heuristic<NQueenBoard> heuristic = new AttackingPairs(n);

        final ObjectiveNode<NQueenBoard> node = hc.searchMin(problem, problem::getInitialState, heuristic, 1000);

        System.out.println(node);
        System.out.println(node.getCost());
        System.out.println(problem.isGoal(node.getState()));
    }
}
