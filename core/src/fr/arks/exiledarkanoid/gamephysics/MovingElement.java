package fr.arks.exiledarkanoid.gamephysics;

public abstract class MovingElement extends Element{

    public Speed speed;

    public MovingElement(Position position, Size size, Speed speed) {
        super(position, size);
        this.speed = speed;
    }

    public MovingElement(MovingElement e) {
        super(e);
        this.speed = new Speed(((MovingElement)e).speed);
    }
}
