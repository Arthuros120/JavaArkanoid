package fr.arks.exiledarkanoid.gamephysics;

public abstract class Element {
    public Position position;
    public Size size;

    public Element(Position position, Size size) {
        this.position = position;
        this.size = size;
    }

    public Element(Element e) {
        this.position = new Position(e.position);
        this.size = new Size(e.size);
    }
}
