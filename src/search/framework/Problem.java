package search.framework;

import java.util.List;

public interface Problem<S,A> {
    S getInitialState();

    List<A> getActions(S state);

    default List<A> getActions(Node<S,A> node) {
        return getActions(node.getState());
    }

    S result(S state, A action);

    default S result(Node<S,A> node, A action) {
        return result(node.getState(), action);
    }

    int stepCost(S from, A action, S to);

    boolean isGoal(S state);

    default boolean isGoal(Node<S,A> node) {
        return isGoal(node.getState());
    }
}
