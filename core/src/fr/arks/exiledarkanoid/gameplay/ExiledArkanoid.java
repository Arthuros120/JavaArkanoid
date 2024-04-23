package fr.arks.exiledarkanoid.gameplay;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.arks.exiledarkanoid.gameplay.screen.GameScreen;

public class ExiledArkanoid extends Game {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 800;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
		Gdx.graphics.setResizable(false);

		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
