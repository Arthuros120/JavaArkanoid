package fr.arks.exiledarkanoid.gamephysics.bases;

/**
 * Position class
 * <p>
 * This class is used to create a position
 */
public class Position {
    public int x;
    public int y;

    /**
     * Constructor
     *
     * @param x The x position
     * @param y The y position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
