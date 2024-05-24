package fr.arks.exiledarkanoid.gameplay.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.arks.exiledarkanoid.gameplay.ExiledArkanoid;

public class StartScreen implements Screen {

    final ExiledArkanoid game;

    Stage stage;

    ImageButton playButton;
    private final Texture background;

    OrthographicCamera camera;

    public StartScreen(final ExiledArkanoid game) {
        this.game = game;
        this.background = new Texture(Gdx.files.internal("background.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWidth(), game.getHeight());

        Texture playTexture = new Texture(Gdx.files.internal("buttons/Play_BTN.png"));
        TextureRegion playTextureRegion = new TextureRegion(playTexture);
        TextureRegionDrawable playTexRegionDrawable = new TextureRegionDrawable(playTextureRegion);
        playButton = new ImageButton(playTexRegionDrawable);
        playButton.setPosition((float) game.getWidth() / 2 - playButton.getWidth() / 2, (float) game.getHeight() / 2 - playButton.getHeight() / 2);
        playButton.addListener(v -> changeToGameScreen(v));
        stage = new Stage(new ScreenViewport());
        stage.addActor(playButton);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean changeToGameScreen(Event event) {
        if (event instanceof InputEvent && ((InputEvent) event).getType() == InputEvent.Type.touchDown) {
            game.setScreen(game.gameScreen);
            return true;
        }
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0, ExiledArkanoid.WIDTH, ExiledArkanoid.HEIGHT);
        game.batch.end();
        stage.draw();
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
        stage.dispose();
    }
}
