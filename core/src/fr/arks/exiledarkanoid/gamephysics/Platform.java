package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.arks.exiledarkanoid.gamephysics.abstracts.Element;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;

/**
 * Platform class
 * <p>
 * This class is used to create a platform
 *
 * @see Element
 */
public class Platform extends Element {

    public final Rectangle shape;
    private final Texture platform_image;

    /**
     * Constructor
     *
     * @param position The position of the platform
     */
    public Platform(Position position) {
        super(position, new Size(0, 0));
        platform_image = new Texture(Gdx.files.internal("paddle.png"));

        this.size = new Size(platform_image.getWidth(), platform_image.getHeight());
        this.shape = new Rectangle(this.position.x, this.position.y, this.size.width, this.size.height);
    }

    /**
     * Change the size of the platform when the player takes a shrink or enlarge bonus
     *
     * @param howManyPixels the number of pixels to add or remove to the platforms width
     */
    public void changeSize(int howManyPixels) {
        this.size.width += howManyPixels;
        this.shape.setSize(this.size.width, this.size.height);
    }

    /**
     * Update the position of the platform according to the mouse position
     *
     * @param gameWidth the width of the game
     */
    public void update(int gameWidth) {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() >= (float) this.size.width / 2.0 && Gdx.input.getX() <= gameWidth - (float) this.size.width / 2.0) {
                this.position.x = Gdx.input.getX() - this.size.width / 2;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        this.shape.setPosition(this.position.x, this.position.y);
        batch.draw(platform_image, this.position.x, this.position.y, this.size.width, this.size.height);
    }

    public void dispose() {
        platform_image.dispose();
    }
}
