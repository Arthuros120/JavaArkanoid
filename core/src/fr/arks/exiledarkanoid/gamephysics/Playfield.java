package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

public class Playfield {
    private final Size size;
    private final Ball ball;
    private final Platform platform;
    private final BrickMap brickMap;

    private final Texture background;

    public Playfield(Size size, int nbBricks) {
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(28, 28), new Speed(200, 200));
        this.platform = new Platform(new Position(size.width / 2, 50), new Size(94, 26), new Speed(1, 0));

        this.background = new Texture(Gdx.files.internal("background.png"));
        this.brickMap = new BrickMap(size, nbBricks, "bricks");

    }

    public void update() {

        if (ball.position.y < platform.position.y + platform.size.height + 100) {
            ball.collideWith(platform);
        }
        ball.collideWith(brickMap);
        ball.collideWith(size);

        platform.update(size.width);
        ball.move();
    }

    public void reset() {
        this.brickMap.generate();
        this.ball.reset(size);
    }

    public boolean isLost() {
        return ball.position.y < platform.position.y;
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, size.width, size.height);
        ball.render(batch);
        platform.render(batch);
        brickMap.render(batch);
    }

    public void dispose() {
        ball.dispose();
        platform.dispose();
        brickMap.dispose();
        background.dispose();
    }
}
