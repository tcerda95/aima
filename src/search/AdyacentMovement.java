package search;

public enum AdyacentMovement {
    UP (1,0),
    RIGHT (0,1),
    DOWN (-1,0),
    LEFT (0,-1);

    private final int rows;
    private final int columns;

    private AdyacentMovement(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
