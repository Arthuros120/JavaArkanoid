package fr.arks.exiledarkanoid.gamephysics.abstracts;

import fr.arks.exiledarkanoid.gamephysics.interfaces.UIComponent;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;

/**
 * This class is used for all the elements that are displayed on the screen
 */
public abstract class Element implements UIComponent {
    public Position position;
    public Size size;

    public Element(Position position, Size size) {
        this.position = position;
        this.size = size;
    }
}
