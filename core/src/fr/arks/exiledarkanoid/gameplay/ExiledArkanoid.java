package fr.arks.exiledarkanoid.gameplay;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gameplay.screen.GameScreen;
import fr.arks.exiledarkanoid.gameplay.screen.StartScreen;

public class ExiledArkanoid extends Game {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;

    public SpriteBatch batch;

    public GameScreen gameScreen;
    public StartScreen startScreen;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
        Gdx.graphics.setResizable(false);

        batch = new SpriteBatch();
        gameScreen = new GameScreen(this);
        startScreen = new StartScreen(this);
        this.setScreen(startScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}
