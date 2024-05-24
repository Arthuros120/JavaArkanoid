package fr.arks.exiledarkanoid.gamephysics.abstracts;

import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

/**
 *
 */
public abstract class MovingElement extends Element {

    public Speed speed;

    public MovingElement(Position position, Size size, Speed speed) {
        super(position, size);
        this.speed = speed;
    }
}
