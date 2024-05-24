package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.bonus.ABonus;
import fr.arks.exiledarkanoid.gamephysics.bases.Pair;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bonus.BonusBrick;

import java.io.File;
import java.util.*;

/**
 * It contains all the bricks of the game
 */
public class BrickMap {
    private final Size size;
    private final ArrayList<Texture> block_images;

    private final int nbBricks;
    private final int nbMaxBricksPerLine = 8;
    private final int brickWidth;
    private final int brickHeight;

    private ArrayList<ArrayList<Brick>> bricks;

    public boolean noCollision = false;

    public BrickMap(Size size, int nbBricks, String pathBrick) {
        this.size = size;
        this.block_images = new ArrayList<>();

        File[] files = new File(pathBrick).listFiles();
        assert files != null;

        for (File file : files) {
            if (file.isFile() && Objects.equals("png", file.getName().split("\\.")[1])) {
                this.block_images.add(new Texture(file.getPath()));
            }
        }

        this.nbBricks = nbBricks;

        this.brickWidth = size.width / this.nbMaxBricksPerLine;
        this.brickHeight = this.brickWidth / 2;
    }

    /**
     * Get all the bricks of the game
     * @return the list of all the bricks
     */
    public ArrayList<Brick> getBricks() {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (ArrayList<Brick> brick : this.bricks) {
            bricks.addAll(brick);
        }
        return bricks;
    }

    /**
     * Generate the bricks of the game with a bonus on some of them
     * @param percentBonusBrick the percentage of bricks that will have a bonus
     * @return the list of bonuses
     */
    public ArrayList<ABonus> generate(int percentBonusBrick) {
        Random rand = new Random();
        this.bricks = new ArrayList<>();
        ArrayList<ABonus> bonuses = new ArrayList<>();

        int nbLines = this.nbBricks / this.nbMaxBricksPerLine;

        if (this.nbBricks % this.nbMaxBricksPerLine != 0) {
            nbLines++;
        }


        for (int line = 0; line < nbLines; line++) {
            ArrayList<Brick> row = new ArrayList<>();
            int y = this.size.height - (line + 1) * this.brickHeight;

            int nbColumns = this.nbBricks - (line * this.nbMaxBricksPerLine);
            if (nbColumns > this.nbMaxBricksPerLine) {
                nbColumns = this.nbMaxBricksPerLine;
            }

            for (int column = 0; column < nbColumns; column++) {
                int x = column * this.brickWidth;
                row.add(
                        new Brick(
                                new Position(x, y),
                                new Size(this.brickWidth, this.brickHeight),
                                block_images.get(rand.nextInt(block_images.size())),
                                new Pair<>(line, column)
                        )
                );
                if (rand.nextInt(100) < percentBonusBrick) {
                    bonuses.add(new BonusBrick(new Position(x, y), new Size(this.brickWidth, this.brickHeight)));
                }
            }
            this.bricks.add(row);
        }

        return bonuses;
    }

    public void removeBrick(Brick brick) {
        for (ArrayList<Brick> row : this.bricks) {
            if (row.contains(brick)) {
                row.remove(brick);
                break;
            }
        }
    }

    public void dispose() {
        for (Brick brick : this.getBricks()) {
            brick.dispose();
        }
    }

    public void render(SpriteBatch batch) {
        for (ArrayList<Brick> row : this.bricks) {
            for (Brick brick : row) {
                brick.render(batch);
            }
        }
    }

    @Override
    public String toString() {
        return "BrickMap{" +
                "size=" + size +
                ", block_images=" + block_images +
                ", nbBricks=" + nbBricks +
                ", nbMaxBricksPerLine=" + nbMaxBricksPerLine +
                ", brickWidth=" + brickWidth +
                ", brickHeight=" + brickHeight +
                ", bricks=" + bricks +
                '}';
    }
}
