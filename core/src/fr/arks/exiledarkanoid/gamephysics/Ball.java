package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.MovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public class Ball extends MovingElement {

    public final Circle shape = new Circle(this.position.x, this.position.y, (float) this.size.width / 2);

    private final Texture ball_image;

    public Ball(Position position, Size size, Speed speed) {
        super(position, size, speed);
        ball_image = new Texture(Gdx.files.internal("ball.png"));
    }

    public void move() {
        if (this.position.x >= 400 - this.size.width || this.position.x <= this.size.width) {
            this.speed.vx = -this.speed.vx;
        }
        if (this.position.y >= 800 - this.size.width) {
            this.speed.vy = -this.speed.vy;
        }
        this.position.x += (int) (this.speed.vx * Gdx.graphics.getDeltaTime());
        this.position.y += (int) (this.speed.vy * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        batch.draw(ball_image, this.position.x, this.position.y, this.size.width, this.size.height);
    }

    @Override
    public void dispose() {
        ball_image.dispose();
    }
}
