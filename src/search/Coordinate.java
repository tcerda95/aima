package search;

import java.util.Objects;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Coordinate))
            return false;

        final Coordinate other = (Coordinate) obj;
        return getX() == other.getX() && getY() == other.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "),(" + y + ")";
    }
}
