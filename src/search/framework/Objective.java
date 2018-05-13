package search.framework;

public interface Objective<S> {
    int value(S state);
}
