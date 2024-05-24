package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import fr.arks.exiledarkanoid.gamephysics.abstracts.MovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Ball class
 *
 * This class is used to create a ball
 *
 * @see MovingElement
 */
public class Ball extends MovingElement {

    public final Circle shape;

    public final Texture ball_image;

    /**
     * Constructor
     *
     * @param position The position of the ball
     * @param speed The speed of the ball
     */
    public Ball(Position position, Speed speed) {
        super(position, new Size(0, 0), speed);
        ball_image = new Texture(Gdx.files.internal("ball.png"));

        this.size.width = ball_image.getWidth();
        this.size.height = ball_image.getHeight();

        this.shape = new Circle(this.position.x, this.position.y, (float) this.size.width / 2);
    }

    /**
     * Move the ball according to its speed
     */
    public void move() {
        this.position.x += (int) (this.speed.vx * Gdx.graphics.getDeltaTime());
        this.position.y += (int) (this.speed.vy * Gdx.graphics.getDeltaTime());
    }

    /**
     * Check if the ball collide with a brick
     * @param brickMap the map of bricks
     * @param brickExplosions the list of explosions that have to be displayed
     * @param noCollision if true, the ball will not change its trajectory when it collides with a brick
     * @return 1 if the ball collide with a brick, 0 otherwise (the score is incremented in the Playfield class)
     */
    public int collideWith(BrickMap brickMap, ArrayList<BrickExplosion> brickExplosions, boolean noCollision) {
        Iterator<Brick> iter = brickMap.getBricks().iterator();
        Vector2 ball_position = new Vector2(this.position.x, this.position.y);
        while (iter.hasNext()) {
            Brick brick = iter.next();
            if (Intersector.overlaps(this.shape, brick.shape)) {
                boolean ball_direction_x = this.speed.vx > 0;
                boolean ball_direction_y = this.speed.vy > 0;
                if (ball_direction_x && ball_direction_y) {
                    Vector2 A = new Vector2(brick.position.x, brick.position.y);
                    Vector2 B = new Vector2(brick.position.x + brick.size.height, brick.position.y + brick.size.height);
                    this.bound(A, B, ball_position, true, noCollision);
                } else if (ball_direction_x) {
                    Vector2 A = new Vector2(brick.position.x, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.height, brick.position.y);
                    this.bound(A, B, ball_position, false, noCollision);
                } else if (ball_direction_y) {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y);
                    this.bound(A, B, ball_position, true, noCollision);
                } else {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y + brick.size.height);
                    this.bound(A, B, ball_position, false, noCollision);
                }
                brickMap.removeBrick(brick);
                BrickExplosion brickExplosion = new BrickExplosion(brick);
                brickExplosions.add(brickExplosion);
                return 1;
            }
        }
        return 0;
    }

    /**
     * Check if the ball collide with a platform
     * @param platform the platform
     * @return true if the ball collide with the platform, false otherwise
     */
    public boolean collideWith(Platform platform) {
        if (this.position.x >= platform.position.x - this.size.width / 2 && this.position.x <= platform.position.x + platform.size.width && this.position.y <= platform.position.y + platform.size.height) {
            this.speed.vy = -this.speed.vy + 10;
            this.speed.vx += (int) (100 * (((float) (this.position.x - platform.position.x) / (float) platform.size.width) - 0.5));
            return true;
        }
        return false;
    }

    /**
     * Check if the ball collide with the sides of the board
     * @param mapSize the size of the board
     */
    public void collideWith(Size mapSize) {
        if (this.position.x >= mapSize.width - this.size.width || this.position.x <= 0) {
            this.speed.vx = -this.speed.vx;
        }
        if (this.position.y >= mapSize.height - this.size.width) {
            this.speed.vy = -this.speed.vy;
        }
    }

    /**
     * Modify the trajectory of the ball when it collides with a brick
     */
    private void bound(Vector2 A, Vector2 B, Vector2 ball_position, boolean ball_direction_y, boolean noCollision) {
        if (!noCollision) {
            int position_relative = positionRelative(A, B, ball_position);
            if (position_relative == 0) {
                this.speed.vx = -this.speed.vx;
                this.speed.vy = -this.speed.vy;
            } else {
                if (ball_direction_y) {
                    if (position_relative == 1) {
                        this.speed.vx = -this.speed.vx;
                    } else {
                        this.speed.vy = -this.speed.vy;
                    }
                } else {
                    if (position_relative == 1) {
                        this.speed.vy = -this.speed.vy;
                    } else {
                        this.speed.vx = -this.speed.vx;
                    }
                }
            }
        }
    }

    /**
     * check if the ball collides under or above the line AB
     * @param A position of the ball
     * @param B
     * @param C
     * @return 1 if the ball is above the line, -1 if it is under, 0 if it is on the line
     */
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

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        batch.draw(ball_image, this.position.x, this.position.y, this.size.width, this.size.height);
    }

    @Override
    public void dispose() {
        ball_image.dispose();
    }

    /**
     * Reset the ball to the center of the board
     * @param size the size of the board
     * @param speed the speed of the ball
     */
    public void reset(Size size, Speed speed) {
        this.position.x = size.width / 2;
        this.position.y = size.height / 2;
        this.speed = speed;
    }
}
