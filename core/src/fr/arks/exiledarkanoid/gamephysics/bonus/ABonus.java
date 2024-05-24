package fr.arks.exiledarkanoid.gamephysics.bonus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.Ball;
import fr.arks.exiledarkanoid.gamephysics.Platform;
import fr.arks.exiledarkanoid.gamephysics.abstracts.AnimatedMovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public abstract class ABonus extends AnimatedMovingElement {

    public final Rectangle shape;
    public final EBonusEffect effect;

    public ABonus(Position position, String pathAsset) {
        super(position, new Size(0, 0), new Speed(0, -300), "bonus/" + pathAsset);

        this.size.width = this.textures[0].getWidth();
        this.size.height = this.textures[0].getHeight();

        this.shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);

        this.effect = EBonusEffect.values()[(int) (Math.random() * EBonusEffect.values().length)];
    }

    public void move() {
        this.position.y += (int) (this.speed.vy * Gdx.graphics.getDeltaTime());
    }

    public boolean isTouched(Platform platform, Ball ball, boolean noCollision) {
        return this.shape.overlaps(platform.shape);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        this.draw(batch, this.position);
    }
}
