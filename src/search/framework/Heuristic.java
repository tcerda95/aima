package search.framework;

public interface Heuristic<S> {
    int cost(S state);
}
