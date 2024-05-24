package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import fr.arks.exiledarkanoid.gamephysics.abstracts.Element;
import fr.arks.exiledarkanoid.gamephysics.abstracts.MovingElement;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

import java.util.Iterator;

public class Ball extends MovingElement {

    public final Circle shape = new Circle(this.position.x, this.position.y, (float) this.size.width / 2);

    private final Texture ball_image;

    public Ball(Position position, Size size, Speed speed) {
        super(position, size, speed);
        ball_image = new Texture(Gdx.files.internal("ball.png"));
    }

    public void move() {
        this.position.x += (int) (this.speed.vx * Gdx.graphics.getDeltaTime());
        this.position.y += (int) (this.speed.vy * Gdx.graphics.getDeltaTime());
    }

    public void collideWith(BrickMap brickMap) {
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
                    this.bound(A, B, ball_position, true);
                } else if (ball_direction_x) {
                    Vector2 A = new Vector2(brick.position.x, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.height, brick.position.y);
                    this.bound(A, B, ball_position, false);
                } else if (ball_direction_y) {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y + brick.size.height);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y);
                    this.bound(A, B, ball_position, true);
                } else {
                    Vector2 A = new Vector2(brick.position.x + brick.size.width - brick.size.height, brick.position.y);
                    Vector2 B = new Vector2(brick.position.x + brick.size.width, brick.position.y + brick.size.height);
                    this.bound(A, B, ball_position, false);
                }
                brickMap.removeBrick(brick);
                break;
            }
        }
    }

    public void collideWith(Platform platform) {
        if (this.position.x >= platform.position.x - this.size.width / 2 && this.position.x <= platform.position.x + platform.size.width && this.position.y <= platform.position.y + platform.size.height) {
            this.speed.vy = - this.speed.vy + 10;
            this.speed.vx += (int) (100 * (((float) (this.position.x - platform.position.x) / (float) platform.size.width) - 0.5));
        }
    }

    public void collideWith(Size mapSize) {
        if (this.position.x >= mapSize.width - this.size.width || this.position.x <= 0) {
            this.speed.vx = -this.speed.vx;
        }
        if (this.position.y >= mapSize.height - this.size.width) {
            this.speed.vy = -this.speed.vy;
        }
    }

    private void bound(Vector2 A, Vector2 B, Vector2 ball_position, boolean ball_direction_y) {
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

    public void reset(Size size) {
        this.position.x = size.width / 2;
        this.position.y = size.height / 2;
        this.speed.vx = 0;
        this.speed.vy = -200;
    }
}
