package fr.arks.exiledarkanoid.gameplay.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

public class WinScreen implements Screen {

    final ExiledArkanoid game;

    public BitmapFont font;

    OrthographicCamera camera;

    public WinScreen(final ExiledArkanoid game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWidth(), game.getHeight());

        font = new BitmapFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        font.draw(game.batch, "YOU WON !!! ", 100, (float) game.getHeight() / 2);
        font.draw(game.batch, "Tap anywhere to restart!", 100, (float) game.getHeight() / 2 - 50);
        font.draw(game.batch, "Score: " + game.gameScreen.playfield.score, 100, (float) game.getHeight() / 2 - 100);
        game.batch.end();

        if (Gdx.input.justTouched()) {
            game.gameScreen.playfield.reset();
            game.setScreen(game.gameScreen);
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
