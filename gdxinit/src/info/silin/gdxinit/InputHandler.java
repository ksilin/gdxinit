package info.silin.gdxinit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;

public class InputHandler implements InputProcessor {

	private WorldController controller;
	private int width;
	private int height;

	public InputHandler(WorldController controller) {
		this.controller = controller;
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
		return true;
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
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// prevent from using the mouse as a single-touch control
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;

		if (x < width / 2 && y > height / 2) {
			controller.leftPressed();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightPressed();
		}
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		// prevent from using the mouse as a single-touch control
		if (!Gdx.app.getType().equals(ApplicationType.Android))
			return false;

		if (x < width / 2 && y > height / 2) {
			controller.leftReleased();
		}
		if (x > width / 2 && y > height / 2) {
			controller.rightReleased();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

}
