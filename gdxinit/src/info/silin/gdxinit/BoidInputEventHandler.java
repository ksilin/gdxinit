package info.silin.gdxinit;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.state.boid.ArrivingMouse;
import info.silin.gdxinit.entity.state.boid.Evading;
import info.silin.gdxinit.entity.state.boid.FleeingMouse;
import info.silin.gdxinit.entity.state.boid.Pursuing;
import info.silin.gdxinit.entity.state.boid.SeekingMouse;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class BoidInputEventHandler extends InputAdapter {

	private static boolean touched = false;
	private static int touchingPointerIndex;

	// @Override
	// public boolean touchDown(int x, int y, int pointer, int button) {
	// touched = true;
	// touchingPointerIndex = pointer;
	//
	// Vector2 unprojectedMousePosition = RendererController
	// .getUnprojectedMousePosition();
	// Gdx.app.log("BoidInput", "new target: " + unprojectedMousePosition);
	//
	// // Levels.STEERING_LAB.getBoid().setTargetPos(unprojectedMousePosition);
	// return super.touchDown(x, y, pointer, button);
	// }

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		touched = false;
		touchingPointerIndex = -1;
		return super.touchUp(x, y, pointer, button);
	}

	@Override
	public boolean keyDown(int keycode) {
		Boid boid = Levels.STEERING_LAB.getBoid();
		if (keycode == Keys.NUM_1) {
			boid.setState(new SeekingMouse());
		}
		if (keycode == Keys.NUM_2) {
			boid.setState(new ArrivingMouse());
		}
		if (keycode == Keys.NUM_3) {
			boid.setState(new FleeingMouse());
		}
		if (keycode == Keys.NUM_4) {
			boid.setState(new Pursuing(Levels.getCurrent().getEnemies().get(0)));
		}
		if (keycode == Keys.NUM_5) {
			boid.setState(new Evading(Levels.getCurrent().getEnemies().get(0)));
		}
		return super.keyUp(keycode);
	}
}
