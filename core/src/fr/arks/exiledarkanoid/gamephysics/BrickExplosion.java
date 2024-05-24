package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.abstracts.AnimatedMovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public class BrickExplosion extends AnimatedMovingElement {

    public BrickExplosion(Brick brick) {
        super(brick.position, new Size(200, 200), new Speed(0, 0), "bricks/brick_explosion", 10);
    }

    public boolean isFinished() {
        return this.currentTextureIndex == this.textures.length - 1;
    }

    @Override
    public void render(SpriteBatch batch) {
        this.draw(batch, this.position);
    }
}
