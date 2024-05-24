package fr.arks.exiledarkanoid.gameplay.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.arks.exiledarkanoid.gamephysics.Playfield;
import fr.arks.exiledarkanoid.gamephysics.bases.Size;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

public class GameScreen implements Screen {

    public final Playfield playfield;
    final ExiledArkanoid game;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public GameScreen(final ExiledArkanoid game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT);

        viewport = new FitViewport(ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT, camera);

        this.playfield = new Playfield(
                new Size(ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT),
                32
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        playfield.update();

        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        game.batch.begin();

        playfield.render(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        game.batch.dispose();
        playfield.dispose();
    }
}
