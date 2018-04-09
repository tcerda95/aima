package search.framework;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

public class Node<S,A> {
    private final S state;
    private final Optional<Node> parent;
    private final Optional<A> action;
    private final int cost;

    public static <S,A> Node<S,A> rootNode(final S state) {
        return new Node<>(state, null, null, 0);
    }

    public static <S,A> Node<S,A> childNode(final Problem<S,A> problem, final Node<S,A> parent, final A action) {
        final S state = problem.result(parent, action);
        final int stepCost = problem.stepCost(parent.getState(), action, state);
        return new Node<>(state, parent, action, stepCost + parent.getPathCost());
    }

    public Node(final S state, final Node parent, final A action, final int cost) {
        if ((Objects.isNull(parent) && Objects.nonNull(action)) || Objects.isNull(action) && Objects.nonNull(parent))
            throw new IllegalStateException("Parent node and action must both be null or non null simultaneously");

        this.state = Objects.requireNonNull(state, "Node state may not be null");
        this.parent = Optional.ofNullable(parent);
        this.action = Optional.ofNullable(action);
        this.cost = cost;
    }

    public S getState() {
        return state;
    }

    public Node getParent() {
        return parent.orElseThrow(NoSuchElementException::new);
    }

    public boolean hasParent() {
        return parent.isPresent();
    }

    public A getAction() {
        return action.orElseThrow(NoSuchElementException::new);
    }

    public int getPathCost() {
        return cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Node))
            return false;

        final Node<?,?> other = (Node) obj;

        if (hasParent() != other.hasParent())
            return false;

        if (!hasParent())
            return getState().equals(other.getState()) && getPathCost() == other.getPathCost();

        return getState().equals(other.getState()) && getAction().equals(other.getAction())
                && getPathCost() == other.getPathCost() && getParent().equals(other.getParent());
    }

    @Override
    public int hashCode() {
        return hasParent() ? Objects.hash(state, parent, action, cost) : Objects.hash(state, cost);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        Node<S,A> node = this;

        while (node.hasParent()) {
            stringBuilder.insert(0, '\n');
            stringBuilder.insert(0, node.getState());
            node = node.getParent();
        }

        stringBuilder.insert(0, '\n');
        stringBuilder.insert(0, node.getState());

        return stringBuilder.toString();
    }
}
