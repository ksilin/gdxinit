package info.silin.gdxinit;

import info.silin.gdxinit.renderer.RendererController;
import info.silin.gdxinit.screens.MenuScreen;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;

public class InputEventHandler extends InputMultiplexer {

	private WorldController controller;
	private RendererController renderer;
	private Vector2 factor = new Vector2(1, 1);
	private int height;

	public InputEventHandler(WorldController controller,
			RendererController renderer) {
		this.controller = controller;
		this.renderer = renderer;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.D) {
			renderer.setDebug(!renderer.isDebug());
		}
		if (keycode == Keys.M) {
			controller.setManualStep(!controller.isManualStep());
		}
		if (keycode == Keys.N) {
			controller.step();
		}
		if (keycode == Keys.ESCAPE) {
			backToMenu();
		}

		if (keycode == Keys.B) {
			controller.togglePause();
		}

		// cam movement
		if (keycode == Keys.I) {
			renderer.moveCamUp();
		}
		if (keycode == Keys.K) {
			renderer.moveCamDown();
		}
		if (keycode == Keys.J) {
			renderer.moveCamLeft();
		}
		if (keycode == Keys.L) {
			renderer.moveCamRight();
		}
		// cam rotation
		if (keycode == Keys.Z) {
			renderer.rotateCamCCW();
		}
		if (keycode == Keys.U) {
			renderer.rotateCamCW();
		}
		// cam zoom
		if (keycode == Keys.O) {
			renderer.zoomOut();
		}
		if (keycode == Keys.P) {
			renderer.zoomIn();
		}
		return super.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return super.keyUp(keycode);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return super.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return super.touchDragged(x, y, pointer);
	}

	public void setSize(int width, int height) {
		this.height = height;
		this.factor = new Vector2(World.WIDTH / width, World.HEIGHT / height);
	}

	public float toWorldX(float x) {
		return x * factor.x;
	}

	public float toWorldY(float y) {
		return (height - y) * factor.y;
	}

	public void backToMenu() {
		GameMain.instance.setScreen(new MenuScreen());
	}
}
