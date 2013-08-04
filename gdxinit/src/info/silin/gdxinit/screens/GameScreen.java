package info.silin.gdxinit.screens;

import info.silin.gdxinit.InputEventHandler;
import info.silin.gdxinit.WorldController;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

	private RendererController renderer = new RendererController(true);
	private WorldController controller = new WorldController();
	private InputEventHandler inputHandler;

	@Override
	public void show() {
		inputHandler = new InputEventHandler(controller, renderer);
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
