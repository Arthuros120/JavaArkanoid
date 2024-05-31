package fr.arks.exiledarkanoid.gamephysics.abstracts;

import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.interfaces.UIComponent;

/**
 * Element class
 * <p>
 * This class is used for all the elements that are displayed on the screen
 */
public abstract class Element implements UIComponent {
    public Position position;
    public Size size;

    /**
     * Constructor
     *
     * @param position The position of the element
     * @param size     The size of the element
     */
    public Element(Position position, Size size) {
        this.position = position;
        this.size = size;
    }
}
