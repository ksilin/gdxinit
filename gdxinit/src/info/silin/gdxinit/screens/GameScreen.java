package info.silin.gdxinit.screens;

import info.silin.gdxinit.GameController;
import info.silin.gdxinit.InputEventHandler;
import info.silin.gdxinit.MyGestureListener;
import info.silin.gdxinit.Screens;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;

public class GameScreen implements Screen {

	private RendererController renderer = new RendererController(true);
	private GameController controller = new GameController();
	private InputEventHandler inputHandler;

	@Override
	public void show() {
		Gdx.app.log("GameScreen", "showing");
		renderer = new RendererController(true);
		controller = new GameController();
		InputMultiplexer inputMultiplexer = Screens.getInputMultiplexer();
		inputMultiplexer.addProcessor(RendererController.uiRenderer.stage);
		inputHandler = new InputEventHandler(controller, renderer);
		inputMultiplexer.addProcessor(inputHandler);
		inputMultiplexer.addProcessor(new GestureDetector(
				new MyGestureListener()));
		Gdx.input.setInputProcessor(inputMultiplexer);
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
		// renderer.setSize(width, height);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO save assets, settings, dispose resources
	}

	@Override
	public void resume() {
		// TODO reload assets, settings, acquire resources
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	public InputEventHandler getInputHandler() {
		return inputHandler;
	}
}
