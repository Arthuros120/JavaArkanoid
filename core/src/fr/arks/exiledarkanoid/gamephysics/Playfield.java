package fr.arks.exiledarkanoid.gamephysics;

import java.util.ArrayList;
import java.util.Iterator;

public class Playfield {
    private Size size;
    private Ball ball;
    private Platform platform;
    private ArrayList<Brick> bricks;

    public Playfield(Size size, int nbBricks) {
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(10, 10), new Speed(2, 2));
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

    }

    public boolean isLost() {
        return ball.position.y < platform.position.y;
    }

    public Ball getBall() {
        return ball;
    }

    public Platform getPlatform() {
        return platform;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }
}
