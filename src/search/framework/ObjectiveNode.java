package search.framework;

import java.util.Objects;

public class ObjectiveNode<S> {
    private S state;
    private int value;

    public ObjectiveNode(S state, int value) {
        this.state = Objects.requireNonNull(state);
        this.value = value;
    }

    public S getState() {
        return state;
    }

    public int getValue() {
        return value;
    }

    public int getCost() {
        return -value;
    }

    public void setState(final S state) {
        this.state = state;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public void setCost(final int cost) {
        setValue(-cost);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof ObjectiveNode))
            return false;

        final ObjectiveNode<?> other = (ObjectiveNode<?>) obj;
        return value == other.value && state.equals(other.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, value);
    }

    @Override
    public String toString() {
        return state.toString();
    }
}
