package fr.arks.exiledarkanoid.gamephysics.bases;

/**
 * Size class
 * <p>
 * This class is used to create a size
 */
public class Size {
    public int width;
    public int height;

    /**
     * Constructor
     *
     * @param width  The width
     * @param height The height
     */
    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Size{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
