package fr.arks.exiledarkanoid.gamephysics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gamephysics.bases.Pair;
import fr.arks.exiledarkanoid.gamephysics.bases.Position;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gamephysics.bases.Speed;
import fr.arks.exiledarkanoid.gamephysics.bonus.ABonus;
import fr.arks.exiledarkanoid.gamephysics.bonus.BonusDrop;
import fr.arks.exiledarkanoid.gamephysics.bonus.EBonusEffect;

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
    private ArrayList<ABonus> bonuses;
    private final ArrayList<Pair<EBonusEffect, Integer>> bonusEffectsActived = new ArrayList<>();
    private String bonusText = "No bonus active";
    private int bonusTextTime = 0;
    private final int timeBonus = 5000;
    private boolean platformIsTouched = false;

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

        spawnBonus();

        if (ball.position.y < platform.position.y + platform.size.height + 100) {
            platformIsTouched = ball.collideWith(platform);
        }
        score += ball.collideWith(brickMap, this.brickMap.noCollision);
        ball.collideWith(size);

        platform.update(size.width);
        ball.move();

        this.bonusCollision();
        this.lifetimeBonus();
        this.checkLifeLost();
    }

    public void reset() {
        this.nbLife = 3;
        this.score = 0;
        this.countdown_next_bonus = new Random().nextInt(1000, 2000);
        this.bonuses = this.brickMap.generate(this.percentBonusBrick);
        this.ball.reset(size);
    }

    private void lifetimeBonus() {
        if (!this.bonusEffectsActived.isEmpty()) {
            ArrayList<Pair<EBonusEffect, Integer>> toRemove = new ArrayList<>();
            for (Pair<EBonusEffect, Integer> bonusEffect : this.bonusEffectsActived) {
                if (bonusEffect.first == EBonusEffect.NO_BLOCK_COLLISION) {
                    if (bonusEffect.second < this.score && this.platformIsTouched) {
                        this.brickMap.noCollision = false;
                        toRemove.add(bonusEffect);
                    }
                } else if (bonusEffect.second > 0) {
                    bonusEffect.second--;
                } else {
                    // to implement
                }
            }
            for (Pair<EBonusEffect, Integer> bonusEffect : toRemove) {
                this.bonusEffectsActived.remove(bonusEffect);
            }
        }
    }

    private void launchBonus(EBonusEffect effect) {
        if (effect == EBonusEffect.NO_BLOCK_COLLISION) {
            this.brickMap.noCollision = true;
            this.bonusEffectsActived.add(new Pair<>(EBonusEffect.NO_BLOCK_COLLISION, this.score));
            System.out.println(this.score);
        }
        this.bonusText = effect.toString();
    }

    private void bonusCollision() {
        ArrayList<ABonus> toRemove = new ArrayList<>();

        if (!this.bonuses.isEmpty()) {
            for (ABonus bonus : bonuses) {
                if (bonus.isTouched(platform, ball, this.brickMap.noCollision)) {
                    launchBonus(bonus.effect);
                    toRemove.add(bonus);
                }
                if (bonus.position.y < 0) {
                    toRemove.add(bonus);
                }
                bonus.move();
            }
        }

        for (ABonus bonus : toRemove) {
            bonuses.remove(bonus);
        }
    }

    private void spawnBonus() {
        if (countdown_next_bonus > 0) {
            countdown_next_bonus--;
        } else {
            bonuses.add(new BonusDrop(new Position((int) (Math.random() * size.width - 15), size.height)));
            countdown_next_bonus = new Random().nextInt(1000, 2000);
        }
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
            batch.draw(ball.ball_image, size.width - 30 - i * 30, 25, ball.ball_image.getWidth(), ball.ball_image.getHeight());
        }
        font.draw(batch, "Score: " + score, 10, 25);
    }

    private void drawBonusInfo(SpriteBatch batch) {
        for (Pair<EBonusEffect, Integer> bonusEffect : this.bonusEffectsActived) {
            font.draw(batch, bonusEffect.first.toString(), 100, 100 + 25 * this.bonusEffectsActived.indexOf(bonusEffect));
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, size.width, size.height);
        ball.render(batch);
        platform.render(batch);
        brickMap.render(batch);
        for (ABonus bonus : bonuses) {
            bonus.render(batch);
        }
        this.drawLife(batch);
        this.drawBonusInfo(batch);
    }

    public void dispose() {
        ball.dispose();
        platform.dispose();
        brickMap.dispose();
        background.dispose();
        for (ABonus bonus : bonuses) {
            bonus.dispose();
        }
    }
}
