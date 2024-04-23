package fr.arks.exiledarkanoid.gamephysics;

public class Playfield {
    private Size size;
    private Ball ball;
    private Platform platform;
    private Brick[] bricks;

    public Playfield(Size size, int nbBricks) {
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(1, 1), new Speed(1, 1));
        this.platform = new Platform(new Position(size.width / 2, size.height - 1), new Size(5, 1), new Speed(1, 0));

        this.bricks = new Brick[nbBricks];

        int brickWidth = size.width / 10;
        int brickHeight = size.height / 20;

        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 10) * brickWidth;
            int y = size.height - (i / 10) * brickHeight;

            bricks[i] = new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2));
        }

    }

    public Ball getBall() {
        return ball;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Brick[] getBricks() {
        return bricks;
    }
}
