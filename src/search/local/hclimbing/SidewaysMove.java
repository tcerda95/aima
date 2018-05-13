package search.local.hclimbing;

import search.framework.Heuristic;
import search.framework.Objective;
import search.framework.ObjectiveNode;
import search.framework.Problem;
import search.queen.AttackingPairs;
import search.queen.NQueenBoard;
import search.queen.NQueenMovement;
import search.queen.NQueenProblem;

public class SidewaysMove<S,A> extends HillClimbing<S,A> {
    private final HillClimbing<S,A> hc;
    private final int maxTries;
    private int tries;

    public SidewaysMove(final HillClimbing<S,A> hc, final int maxTries) {
        this.hc = hc;
        this.maxTries = maxTries;
    }

    @Override
    protected ObjectiveNode<S> findSuccessor(final ObjectiveNode<S> node, final Problem<S, A> problem, final Objective<S> objective) {
        return hc.findSuccessor(node, problem, objective);
    }

    @Override
    protected boolean shouldSearch(final ObjectiveNode<S> node, final ObjectiveNode<S> successor) {
        if (successor.getValue() > node.getValue()) {
            tries = 0;
            return true;
        }

        if (successor.getValue() == node.getValue()) {
            tries += 1;
            return tries <= maxTries;
        }

        return false;
    }

    public static void main(String[] args) {
        final int n = 8;
        final Problem<NQueenBoard, NQueenMovement> problem = new NQueenProblem(n);
        final HillClimbing<NQueenBoard, NQueenMovement> hc = new SidewaysMove<>(new SteepestAscent<>(), 100);
        final Heuristic<NQueenBoard> heuristic = new AttackingPairs(n);

        int successCount = 0;

        for (int i = 0; i < 1000; i++) {
            final ObjectiveNode<NQueenBoard> node = hc.searchMin(problem, heuristic);
            successCount += problem.isGoal(node.getState()) ? 1 : 0;
        }

        System.out.println(successCount);
    }
}
