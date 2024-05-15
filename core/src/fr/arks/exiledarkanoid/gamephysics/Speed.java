package fr.arks.exiledarkanoid.gamephysics;

public class Speed {
    public int vx;
    public int vy;

    public Speed(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public Speed(Speed s) {
        this.vx = s.vx;
        this.vy = s.vy;
    }
}
