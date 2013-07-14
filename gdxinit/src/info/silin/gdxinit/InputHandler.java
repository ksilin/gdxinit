package info.silin.gdxinit;

import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import info.silin.gdxinit.screens.GameScreen;

public class InputHandler extends InputMultiplexer {

	private WorldController controller;
	private RendererController renderer;
	private GameScreen screen;
	private int width;
	private int height;

	public InputHandler(WorldController controller,
			RendererController renderer, GameScreen screen) {
		this.controller = controller;
		this.renderer = renderer;
		this.screen = screen;
		this.addProcessor(renderer.getStage());
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.LEFT) {
			controller.leftPressed();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightPressed();
		}
		if (keycode == Keys.UP) {
			controller.upPressed();
		}
		if (keycode == Keys.DOWN) {
			controller.downPressed();
		}
		if (keycode == Keys.X) {
			controller.firePressed();
		}
		if (keycode == Keys.D) {
			renderer.setDebug(!renderer.isDebug());
		}
		if (keycode == Keys.ESCAPE) {
			screen.backToMenu();
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.LEFT) {
			controller.leftReleased();
		}
		if (keycode == Keys.RIGHT) {
			controller.rightReleased();
		}
		if (keycode == Keys.UP) {
			controller.upReleased();
		}
		if (keycode == Keys.DOWN) {
			controller.downReleased();
		}
		if (keycode == Keys.X) {
			controller.fireReleased();
		}
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// prevent from using the mouse as a single-touch control
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;

		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// prevent from using the mouse as a single-touch control
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;

		return super.touchUp(x, y, pointer, button);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
