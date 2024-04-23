package fr.arks.exiledarkanoid.gameplay.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.arks.exiledarkanoid.gamephysics.Brick;
import fr.arks.exiledarkanoid.gamephysics.Playfield;
import fr.arks.exiledarkanoid.gamephysics.Size;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

public class GameScreen implements Screen {

    final ExiledArkanoid game;
    final Playfield playfield;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final ShapeRenderer shapeRenderer;

    Array<Rectangle> bricks;

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

        bricks = new Array<Rectangle>();

        for (Brick brick : playfield.getBricks()) {
            Rectangle rectangle = new Rectangle();
            rectangle.x = brick.position.x;
            rectangle.y = brick.position.y;
            rectangle.width = brick.size.width;
            rectangle.height = brick.size.height;
            bricks.add(rectangle);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (Rectangle brick : bricks) {
            shapeRenderer.rect(brick.x, brick.y, brick.width, brick.height);
        }

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
