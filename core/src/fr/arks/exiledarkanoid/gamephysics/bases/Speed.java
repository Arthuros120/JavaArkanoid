package fr.arks.exiledarkanoid.gamephysics.bases;

/**
 * Speed class
 * <p>
 * This class is used to create a speed
 */
public class Speed {
    public int vx;
    public int vy;

    /**
     * Constructor
     *
     * @param vx The x speed
     * @param vy The y speed
     */
    public Speed(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public String toString() {
        return "Speed{" +
                "vx=" + vx +
                ", vy=" + vy +
                '}';
    }
}
