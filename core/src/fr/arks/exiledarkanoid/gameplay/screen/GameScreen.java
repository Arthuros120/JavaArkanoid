package fr.arks.exiledarkanoid.gameplay.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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
    private final ShapeRenderer shapeRenderer;

    public GameScreen(final ExiledArkanoid game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT);

        viewport = new FitViewport(ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT, camera);

        shapeRenderer = new ShapeRenderer();

        this.playfield = new Playfield(
                new Size(ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT),
                30
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        playfield.update();

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (touchPos.x >= (float) playfield.getPlatform().size.width / 2 && touchPos.x <= ExiledArkanoid.WIDTH - (float) playfield.getPlatform().size.width / 2) {
                playfield.getPlatform().position.x = (int) (touchPos.x - playfield.getPlatform().size.width / 2);
            }
        }

        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        playfield.render(shapeRenderer);

        shapeRenderer.end();
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
        shapeRenderer.dispose();
    }
}
