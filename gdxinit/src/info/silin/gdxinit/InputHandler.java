package info.silin.gdxinit;

import info.silin.gdxinit.renderer.RendererController;
import info.silin.gdxinit.screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;

public class InputHandler extends InputMultiplexer {

	private WorldController controller;
	private RendererController renderer;
	private GameScreen screen;
	private int width;
	private int height;
	private Vector2 factor = new Vector2(1, 1);

	public InputHandler(WorldController controller,
			RendererController renderer, GameScreen screen) {
		this.controller = controller;
		this.renderer = renderer;
		this.screen = screen;
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
		if (keycode == Keys.M) {
			controller.setManualStep(!controller.isManualStep());
		}
		if (keycode == Keys.K) {
			controller.step();
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

		if (button == Buttons.LEFT) {
			controller.leftMouseDown(toWorldX(x), toWorldY(y));
		}

		// prevent from using the mouse as a single-touch control
		// if (!Gdx.app.getType().equals(ApplicationType.Android))
		// return false;

		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {

		if (button == Buttons.LEFT) {
			controller.leftMouseUp(toWorldX(x), toWorldY(y));
		}

		// prevent from using the mouse as a single-touch control
		// if (!Gdx.app.getType().equals(ApplicationType.Android))
		// return false;

		return super.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {

		controller.mousePosition.x = toWorldX(x);
		controller.mousePosition.y = toWorldY(y);

		return super.touchDragged(x, y, pointer);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;

		this.factor = new Vector2(WorldController.WIDTH / width,
				WorldController.HEIGHT / height);

		Gdx.app.log("InputHandler", "factor: x: " + factor.x + ", y: "
				+ factor.y);
	}

	public float toWorldX(float x) {
		return x * factor.x;
	}

	public float toWorldY(float y) {
		return WorldController.HEIGHT - y * factor.y;
	}

}
