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

public abstract class Bonus extends AnimatedMovingElement {

    public final Rectangle shape;

    public Bonus(Position position, String pathAsset) {
        super(position, new Size(0, 0), new Speed(0, -200), "bonus/" + pathAsset);

        this.size.width = this.textures[0].getWidth();
        this.size.height = this.textures[0].getHeight();

        this.shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);
    }

    public void move() {
        this.position.y += (int) (this.speed.vy * Gdx.graphics.getDeltaTime());
    }

    public boolean isTouched(Platform platform, Ball ball) {
        return this.shape.overlaps(platform.shape);
    }

    public void apply() {
        // To be implemented in child classes
        // Todo: a faire
        System.out.println("Apply bonus");
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        this.draw(batch, this.position);
    }
}
