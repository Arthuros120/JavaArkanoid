package fr.arks.exiledarkanoid.gamephysics;

public class Ball extends MovingElement {

    public Ball(Position position, Size size, Speed speed) {
        super(position, size, speed);
    }

    public Ball(MovingElement e) {
        super(e);
    }

    public void move() {
        this.position.x += this.speed.vx;
        this.position.y += this.speed.vy;
    }

}
