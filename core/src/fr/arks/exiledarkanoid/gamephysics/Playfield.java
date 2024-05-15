package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

import java.util.ArrayList;
import java.util.Iterator;

public class Playfield {
    private final int nbBricks;
    private final Size size;
    private final Ball ball;
    private final Platform platform;
    private ArrayList<Brick> bricks;

    public Playfield(Size size, int nbBricks) {
        this.nbBricks = nbBricks;
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(10, 10), new Speed(4, 4));
        this.platform = new Platform(new Position(size.width / 2, 50), new Size(80, 20), new Speed(1, 0));

        this.bricks = new ArrayList<>();

        int brickWidth = size.width / 10;
        int brickHeight = size.height / 20;

        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 10) * brickWidth;
            int y = size.height - (i / 10) * brickHeight;

            bricks.add(new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2)));
        }

    }

    public void update() {
        ball.move();
        Iterator<Brick> iter = bricks.iterator();
        while (iter.hasNext()) {
            Brick brick = iter.next();
            if (ball.position.x >= brick.position.x && ball.position.x <= brick.position.x + brick.size.width && (ball.position.y >= brick.position.y - ball.size.width || (ball.position.y <= brick.position.y + brick.size.height + ball.size.width && ball.position.y >= brick.position.y + brick.size.height))) {
                ball.speed.vy = -ball.speed.vy;
                iter.remove();
                break;
            } else if (ball.position.y >= brick.position.y && ball.position.y <= brick.position.y + brick.size.height && (ball.position.x >= brick.position.x - ball.size.width || (ball.position.x <= brick.position.x + brick.size.width + ball.size.height && ball.position.x >= brick.position.x + brick.size.width))) {
                ball.speed.vx = -ball.speed.vx;
                iter.remove();
                break;
            }
        }
        if (ball.position.x >= platform.position.x && ball.position.x <= platform.position.x + platform.size.width && ball.position.y <= platform.position.y + platform.size.height + ball.size.width) {
            ball.speed.vy = -ball.speed.vy;
            ball.speed.vx += (2 * (ball.position.x - platform.position.x) / platform.size.width) - 1;
        }
    }

    public void reset() {
        this.bricks = new ArrayList<>();
        int brickWidth = size.width / 10;
        int brickHeight = size.height / 20;
        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 10) * brickWidth;
            int y = size.height - (i / 10) * brickHeight;

            bricks.add(new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2)));
        }

        ball.position.x = size.width / 2;
        ball.position.y = size.height / 2;
        ball.speed.vx = 4;
        ball.speed.vy = 4;
    }

    public boolean isLost() {
        return ball.position.y < platform.position.y;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void render(ShapeRenderer shapeRenderer) {
        ball.render(shapeRenderer);
        platform.render(shapeRenderer);
        for (Brick brick : bricks) {
            brick.render(shapeRenderer);
        }
    }
}
