package fr.arks.exiledarkanoid.gamephysics;

public class Platform extends MovingElement {

    public Platform(Position position, Size size, Speed speed) {
        super(position, size, speed);
    }

    public Platform(MovingElement e) {
        super(e);
    }

    public void moveLeft() {
        this.position.x -= this.speed.vx;
    }

    public void moveRight() {
        this.position.x += this.speed.vx;
    }
}
