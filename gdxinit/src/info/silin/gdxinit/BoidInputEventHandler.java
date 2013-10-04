package info.silin.gdxinit;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.steering.ArriveSteering;
import info.silin.gdxinit.entity.steering.FleeSteering;
import info.silin.gdxinit.entity.steering.SeekSteering;
import info.silin.gdxinit.renderer.RendererController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

public class BoidInputEventHandler extends InputAdapter {

	private static boolean touched = false;
	private static int touchingPointerIndex;

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		touched = true;
		touchingPointerIndex = pointer;

		Vector2 unprojectedMousePosition = RendererController
				.getUnprojectedMousePosition();
		Gdx.app.log("BoidInput", "new target: " + unprojectedMousePosition);

		Levels.STEERING_LAB.getBoid().setTargetPos(unprojectedMousePosition);
		return super.touchDown(x, y, pointer, button);
	}

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
			boid.setCurrent(new SeekSteering());
		}
		if (keycode == Keys.NUM_2) {
			boid.setCurrent(new FleeSteering());
		}
		if (keycode == Keys.NUM_3) {
			boid.setCurrent(new ArriveSteering());
		}
		return super.keyUp(keycode);
	}
}
