package fr.arks.exiledarkanoid.gamephysics.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * AnimatedMovingElement class
 * <p>
 * This class is used to create an animated moving element
 *
 * @see MovingElement
 */
public abstract class AnimatedMovingElement extends MovingElement {

    public final Texture[] textures;
    public int animationSpeed;

    public int currentTextureIndex = 0;
    private int rate = 0;

    /**
     * Constructor
     *
     * @param position       The position of the animated moving element
     * @param size           The size of the animated moving element
     * @param speed          The speed of the animated moving element
     * @param pathAsset      The path of the asset
     * @param animationSpeed The speed of the animation
     */
    public AnimatedMovingElement(Position position, Size size, Speed speed, String pathAsset, int animationSpeed) {
        super(position, size, speed);

        this.animationSpeed = animationSpeed;
        this.textures = this.loadAsset(pathAsset);
    }

    /**
     * Constructor
     *
     * @param position  The position of the animated moving element
     * @param size      The size of the animated moving element
     * @param speed     The speed of the animated moving element
     * @param pathAsset The path of the asset
     */
    public AnimatedMovingElement(Position position, Size size, Speed speed, String pathAsset) {
        this(position, size, speed, pathAsset, 5);
    }

    /**
     * Load the asset
     *
     * @param pathAsset The path of the asset
     * @return The textures
     */
    private Texture[] loadAsset(String pathAsset) {
        File[] files = new File(pathAsset).listFiles();
        assert files != null;

        ArrayList<File> tmpFiles = new ArrayList<>();

        for (File file : files) {
            if (file.isFile() && Objects.equals("png", file.getName().split("\\.")[1])) {
                tmpFiles.add(file);
            }
        }

        Texture[] textures = new Texture[tmpFiles.size()];

        for (File file : tmpFiles) {
            String name = file.getName().split("\\.")[0];
            String[] nameSplit = name.split("_");
            int index = Integer.parseInt(nameSplit[nameSplit.length - 1]) - 1;
            textures[index] = new Texture(file.getPath());
        }

        return textures;
    }

    /**
     * Draw the animated moving element
     *
     * @param batch    The batch
     * @param position The position
     */
    public void draw(SpriteBatch batch, Position position) {
        if (this.rate < this.animationSpeed) {
            this.rate++;
            batch.draw(this.textures[this.currentTextureIndex], position.x, position.y);
            return;
        }

        this.rate = 0;
        this.currentTextureIndex++;

        if (this.currentTextureIndex >= this.textures.length) {
            this.currentTextureIndex = 0;
        }
        batch.draw(this.textures[this.currentTextureIndex], position.x, position.y);
    }

    /**
     * Render the animated moving element
     */
    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
    }

}
