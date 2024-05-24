package fr.arks.exiledarkanoid.gamephysics.abstracts;

import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

/**
 * MovingElement class
 *
 * This class is used for all the elements that are moving on the screen
 *
 * @see Element
 * @see Speed
 * @see Position
 * @see Size
 */
public abstract class MovingElement extends Element {

    public Speed speed;

    /**
     * Constructor
     *
     * @param position The position of the moving element
     * @param size The size of the moving element
     * @param speed The speed of the moving element
     */
    public MovingElement(Position position, Size size, Speed speed) {
        super(position, size);
        this.speed = speed;
    }
}
