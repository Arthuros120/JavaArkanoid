package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Playfield {
    private final int nbBricks;
    private final Size size;
    private final Ball ball;
    private final Platform platform;
    private ArrayList<Brick> bricks;

    private final ArrayList<Texture> block_images;
    private final Texture background;

    public Playfield(Size size, int nbBricks) {
        this.nbBricks = nbBricks;
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(16, 16), new Speed(1, 1));
        this.platform = new Platform(new Position(size.width / 2, 50), new Size(96, 28), new Speed(1, 0));

        block_images = new ArrayList<>();
        block_images.add(new Texture(Gdx.files.internal("Colored_Blue_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Green_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Red_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Yellow_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Orange_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Purple_Block-64x32.png")));

        background = new Texture(Gdx.files.internal("background.png"));

        this.bricks = new ArrayList<>();

        int brickWidth = size.width / 5;
        int brickHeight = size.height / 20;

        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 5) * brickWidth;
            int y = size.height - (i / 5) * brickHeight;

            Random rand = new Random();

            Texture block_image = block_images.get(rand.nextInt(block_images.size()));

            bricks.add(new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2), block_image));
        }

    }

    public void update() {
        ball.move();
        Iterator<Brick> iter = bricks.iterator();
        while (iter.hasNext()) {
            Brick brick = iter.next();
            if (Intersector.overlaps(ball.shape, brick.shape)) {
                float overlapX;
                float overlapY;

                if (ball.position.x < brick.position.x) {
                    overlapX = ball.position.x + ball.size.width - brick.position.x;
                } else {
                    overlapX = brick.position.x + brick.size.width - ball.position.x;
                }

                if (ball.position.y < brick.position.y) {
                    overlapY = ball.position.y + ball.size.height - brick.position.y;
                } else {
                    overlapY = brick.position.y + brick.size.height - ball.position.y;
                }

                if (overlapX < overlapY) {
                    ball.speed.vy = -ball.speed.vy;
                }
                else {
                    ball.speed.vx = -ball.speed.vx;
                }
                iter.remove();
            }
        }
        if (ball.position.x >= platform.position.x - ball.size.width / 2 && ball.position.x <= platform.position.x + platform.size.width && ball.position.y <= platform.position.y + platform.size.height) {
            ball.speed.vy = -ball.speed.vy;
            ball.speed.vx += (2 * (ball.position.x - platform.position.x) / platform.size.width) - 1;
        }
    }

    public void reset() {
        this.bricks = new ArrayList<>();
        int brickWidth = size.width / 10;
        int brickHeight = size.height / 40;

        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 10) * brickWidth;
            int y = size.height - (i / 10) * brickHeight;

            Random rand = new Random();

            Texture block_image = block_images.get(rand.nextInt(block_images.size()));

            bricks.add(new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2), block_image));
        }

        ball.position.x = size.width / 2;
        ball.position.y = size.height / 2;
        ball.speed.vx = 2;
        ball.speed.vy = 2;
    }

    public boolean isLost() {
        return ball.position.y < platform.position.y;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, size.width, size.height);
        ball.render(batch);
        platform.render(batch);
        for (Brick brick : bricks) {
            brick.render(batch);
        }
    }
}
