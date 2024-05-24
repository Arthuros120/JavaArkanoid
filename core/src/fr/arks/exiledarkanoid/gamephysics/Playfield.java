package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.bonus.Bonus;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;
import fr.arks.exiledarkanoid.gamephysics.bonus.BonusDrop;

import java.util.ArrayList;
import java.util.Random;

public class Playfield {
    private final Texture background;
    private final Size size;
    private final Ball ball;
    private final Platform platform;
    private final BrickMap brickMap;

    private int nbLife;
    private int countdown_next_bonus = new Random().nextInt(1000, 2000);
    private ArrayList<Bonus> bonuses;
    private final int percentBonusBrick = 80;

    public int score;

    public BitmapFont font;

    public Playfield(Size size, int nbBricks) {
        this.size = size;
        this.ball = new Ball(new Position(size.width / 2, size.height / 2), new Speed(200, 200));
        this.platform = new Platform(new Position(size.width / 2, 50));
        this.nbLife = 3;
        this.score = 0;

        this.background = new Texture(Gdx.files.internal("background.png"));
        this.brickMap = new BrickMap(size, nbBricks, "bricks");

        this.font = new BitmapFont();

        this.bonuses = this.brickMap.generate(this.percentBonusBrick);
    }

    public void update() {

        if (countdown_next_bonus > 0) {
            countdown_next_bonus--;
        } else {
            bonuses.add(new BonusDrop(new Position((int) (Math.random() * size.width - 15), size.height)));
            countdown_next_bonus = new Random().nextInt(1000, 2000);
        }

        if (ball.position.y < platform.position.y + platform.size.height + 100) {
            ball.collideWith(platform);
        }
        score += ball.collideWith(brickMap);
        ball.collideWith(size);

        platform.update(size.width);
        ball.move();

        ArrayList<Bonus> toRemove = new ArrayList<>();

        if (!this.bonuses.isEmpty()) {
            for (Bonus bonus : bonuses) {
                if (bonus.isTouched(platform, ball)) {
                    //bonus.apply(platform, ball);
                    toRemove.add(bonus);
                }
                if (bonus.position.y < 0) {
                    toRemove.add(bonus);
                }
                bonus.move();
            }
        }

        for (Bonus bonus : toRemove) {
            bonuses.remove(bonus);
        }

        this.checkLifeLost();
    }

    public void reset() {
        this.nbLife = 3;
        this.score = 0;
        this.countdown_next_bonus = new Random().nextInt(1000, 2000);
        this.bonuses = this.brickMap.generate(this.percentBonusBrick);
        this.ball.reset(size);
    }

    public boolean isLost() {
        return nbLife <= 0;
    }

    private void checkLifeLost() {
        if (ball.position.y <= platform.position.y) {
            nbLife--;
            this.ball.reset(size);
        }
    }

    private void drawLife(SpriteBatch batch) {
        for (int i = 0; i < nbLife; i++) {
            batch.draw(ball.ball_image, size.width - 30 - i * 30, 25, 20, 20);
        }
        font.draw(batch, "Score: " + score, 10, 25);
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, size.width, size.height);
        ball.render(batch);
        platform.render(batch);
        brickMap.render(batch);
        for (Bonus bonus : bonuses) {
            bonus.render(batch);
        }
        this.drawLife(batch);
    }

    public void dispose() {
        ball.dispose();
        platform.dispose();
        brickMap.dispose();
        background.dispose();
        for (Bonus bonus : bonuses) {
            bonus.dispose();
        }
    }
}
