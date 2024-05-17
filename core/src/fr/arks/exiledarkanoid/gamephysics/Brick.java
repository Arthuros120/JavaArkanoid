package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.Element;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;


public class Brick extends Element {

    public final Rectangle shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);

    private final Texture block_image;

    public Brick(Position position, Size size, Texture block_image) {
        super(position, size);
        this.block_image = block_image;
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        batch.draw(block_image, this.position.x, this.position.y, this.size.width, this.size.height);
    }

    @Override
    public void dispose() {
        block_image.dispose();
    }
}
