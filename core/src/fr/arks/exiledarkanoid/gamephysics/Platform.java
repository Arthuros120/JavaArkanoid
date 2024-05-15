package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.MovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public class Platform extends MovingElement {

    public final Rectangle shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);

    public Platform(Position position, Size size, Speed speed) {
        super(position, size, speed);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        this.shape.setPosition(this.position.x, this.position.y);
        shapeRenderer.rect(this.position.x, this.position.y, this.size.width, this.size.height);
    }
}
