package fr.arks.exiledarkanoid.gamephysics;

public class Size {
    public int width;
    public int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Size(Size s) {
        this.width = s.width;
        this.height = s.height;
    }
}
