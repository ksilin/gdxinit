package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameInputHandler;
import info.silin.gdxinit.World;
import info.silin.gdxinit.WorldController;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	private World world;
	private RendererController renderer;
	private WorldController controller;
	private GameInputHandler inputHandler;
	private Game game;

	public GameScreen(Game game) {
		this.game = game;
	}

	@Override
	public void show() {
		world = new World();
		renderer = new RendererController(world, true);
		controller = new WorldController(world, renderer);

		inputHandler = new GameInputHandler(controller, renderer, game);
		inputHandler.addProcessor(renderer.getStage());
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render(float delta) {
		if (!controller.isManualStep()) {
			controller.update(delta);
		}
		renderer.draw(delta);
	}

	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
		inputHandler.setSize(width, height);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
}
