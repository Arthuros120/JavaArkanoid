package fr.arks.exiledarkanoid.gamephysics.bonus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.Ball;
import fr.arks.exiledarkanoid.gamephysics.Platform;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;

public class BonusBrick extends Bonus {

    public boolean isTouched = false;

    public BonusBrick(Position position, Size size) {
        super(position, "bonus_brick");
        this.shape.setSize(size.width, size.height);
    }

    @Override
    public void move() {
        if (isTouched) {
            super.move();
        }
    }

    @Override
    public boolean isTouched(Platform platform, Ball ball) {
        if (this.shape.overlaps(platform.shape) && isTouched) {
            return true;
        }
        if (this.shape.overlaps(new Rectangle(ball.position.x, ball.position.y, ball.size.width, ball.size.height)) && !isTouched) {
            isTouched = true;
            this.size.width = this.textures[0].getWidth();
            this.size.height = this.textures[0].getHeight();
            return false;
        }

        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isTouched) {
            super.render(batch);
        }
    }


}
