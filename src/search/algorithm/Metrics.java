package search.algorithm;

import java.util.Objects;

public class Metrics {
    private final long timeElapsed;
    private final int iterations;

    public Metrics(final long timeElapsed, final int iterations) {
        this.timeElapsed = timeElapsed;
        this.iterations = iterations;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public int getIterations() {
        return iterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Metrics))
            return false;

        final Metrics metrics = (Metrics) o;
        return getTimeElapsed() == metrics.getTimeElapsed() && getIterations() == metrics.getIterations();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimeElapsed(), getIterations());
    }

    @Override
    public String toString() {
        return "{ timeElapsed: " + timeElapsed + ", iterations: " + iterations + " }";
    }
}
