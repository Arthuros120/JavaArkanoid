package fr.arks.exiledarkanoid.gamephysics;

public class Ball extends MovingElement {

    public Ball(Position position, Size size, Speed speed) {
        super(position, size, speed);
    }

    public Ball(MovingElement e) {
        super(e);
    }

    public void move() {
        if (this.position.x >= 400 - this.size.width || this.position.x <= this.size.width){
            this.speed.vx = - this.speed.vx;
        }
        if (this.position.y >= 800 - this.size.width || this.position.y <= this.size.width){
            this.speed.vy = - this.speed.vy;
        }
        this.position.x += this.speed.vx;
        this.position.y += this.speed.vy;
    }

}
