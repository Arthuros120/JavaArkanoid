package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.MovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public class Platform extends MovingElement {

    public final Rectangle shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);

    private final Texture platform_image;

    public Platform(Position position, Size size, Speed speed) {
        super(position, size, speed);
        platform_image = new Texture(Gdx.files.internal("paddle_94x26.png"));
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        batch.draw(platform_image, this.position.x, this.position.y, this.size.width, this.size.height);
    }

    public void dispose() {
        platform_image.dispose();
    }
}
