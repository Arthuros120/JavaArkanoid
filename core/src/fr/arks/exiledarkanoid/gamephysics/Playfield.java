package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

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
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Size(28, 28), new Speed(200, 200));
        this.platform = new Platform(new Position(size.width / 2, 50), new Size(94, 26), new Speed(1, 0));

        block_images = new ArrayList<>();
        block_images.add(new Texture(Gdx.files.internal("Colored_Blue_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Green_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Red_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Yellow_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Orange_Block-64x32.png")));
        block_images.add(new Texture(Gdx.files.internal("Colored_Purple_Block-64x32.png")));

        background = new Texture(Gdx.files.internal("background.png"));

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

    }

    public void update() {
        ball.move();
        Iterator<Brick> iter = bricks.iterator();
        Vector2 ball_position = new Vector2(ball.position.x, ball.position.y);
        while (iter.hasNext()) {
            Brick brick = iter.next();
            if (Intersector.overlaps(ball.shape, brick.shape)) {
                boolean ball_direction_x = ball.speed.vx > 0;
                boolean ball_direction_y = ball.speed.vy > 0;
                if (ball_direction_x && ball_direction_y){
                    Vector2 A = new Vector2(brick.position.x, brick.position.y);
                    Vector2 B = new Vector2(brick.position.x + brick.size.height, brick.position.y + brick.size.height);
                    this.bound(A, B, ball_position, ball_direction_y, ball);
                }
                else if (ball_direction_x && !ball_direction_y) {
                    Vector2 A = new Vector2(brick.position.x, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.height, brick.position.y);
                    this.bound(A, B, ball_position, ball_direction_y, ball);
                }
                else if (!ball_direction_x && ball_direction_y) {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y);
                    this.bound(A, B, ball_position, ball_direction_y, ball);
                }
                else {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y + brick.size.height);
                    this.bound(A, B, ball_position, ball_direction_y, ball);
                }
                iter.remove();
                break;
            }
        }
        if (ball.position.x >= platform.position.x - ball.size.width / 2 && ball.position.x <= platform.position.x + platform.size.width && ball.position.y <= platform.position.y + platform.size.height) {
            ball.speed.vy = -ball.speed.vy + 10;
            ball.speed.vx += (int) (100 * (( (float) ( ball.position.x - platform.position.x) / (float) platform.size.width) - 0.5));
        }
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            if (touchPos.x >= (float) platform.size.width / 2.0 && touchPos.x <= ExiledArkanoid.WIDTH - (float) platform.size.width / 2.0) {
                platform.position.x = (int) (touchPos.x - platform.size.width / 2);
            }
        }
    }

    public void reset() {
        this.bricks = new ArrayList<>();
        int brickWidth = size.width / 8;
        int brickHeight = size.height / 32;

        for (int i = 0; i < nbBricks; i++) {
            int x = (i % 10) * brickWidth;
            int y = size.height - (i / 10) * brickHeight;

            Random rand = new Random();

            Texture block_image = block_images.get(rand.nextInt(block_images.size()));

            bricks.add(new Brick(new Position(x, y - brickHeight), new Size(brickWidth - 2, brickHeight - 2), block_image));
        }

        ball.position.x = size.width / 2 + 5;
        ball.position.y = size.height / 2;
        ball.speed.vx = 0;
        ball.speed.vy = -200;
    }

    public boolean isLost() {
        return ball.position.y < platform.position.y;
    }

    private static int positionRelative(Vector2 A, Vector2 B, Vector2 C) {
        /*
         A function to know if the point C is under or above the line AB
         */
        float crossProduct = (B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x);
        if (crossProduct > 0) {
            return 1;
        } else if (crossProduct < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private void bound(Vector2 A, Vector2 B, Vector2 ball_position, boolean ball_direction_y, Ball ball) {
        int position_relative = positionRelative(A, B, ball_position);
        if (position_relative == 0) {
            ball.speed.vx = -ball.speed.vx;
            ball.speed.vy = -ball.speed.vy;
        }
        else {
            if (ball_direction_y) {
                if (position_relative == 1) {
                    ball.speed.vx = -ball.speed.vx;
                }
                else {
                    ball.speed.vy = -ball.speed.vy;
                }
            } else {
                if (position_relative == 1) {
                    ball.speed.vy = -ball.speed.vy;
                }
                else {
                    ball.speed.vx = -ball.speed.vx;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, size.width, size.height);
        ball.render(batch);
        platform.render(batch);
        for (Brick brick : bricks) {
            brick.render(batch);
        }
    }

    public void dispose() {
        ball.dispose();
        platform.dispose();
        for (Brick brick: bricks) {
            brick.dispose();
        }
        background.dispose();
    }
}
