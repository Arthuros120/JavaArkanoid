package fr.arks.exiledarkanoid.gamephysics.bases;

public class Speed {
    public int vx;
    public int vy;

    public Speed(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public String toString() {
        return "Speed{" +
                "vx=" + vx +
                ", vy=" + vy +
                '}';
    }
}
