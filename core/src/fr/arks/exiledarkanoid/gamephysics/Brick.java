package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.Element;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;

public class Brick extends Element {

    public final Rectangle shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);

    public Brick(Position position, Size size) {
        super(position, size);
    }

    @Override
    public void render(ShapeRenderer shapeRenderer) {
        this.shape.setPosition(this.position.x, this.position.y);
        shapeRenderer.rect(this.position.x, this.position.y, this.size.width, this.size.height);
    }
}
