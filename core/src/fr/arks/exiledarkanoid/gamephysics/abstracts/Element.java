package fr.arks.exiledarkanoid.gamephysics.abstracts;

import fr.arks.exiledarkanoid.gamephysics.interfaces.UIComponent;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;

public abstract class Element implements UIComponent {
    public Position position;
    public Size size;

    public Element(Position position, Size size) {
        this.position = position;
        this.size = size;
    }
}
