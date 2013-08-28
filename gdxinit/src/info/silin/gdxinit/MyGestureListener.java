package info.silin.gdxinit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class MyGestureListener implements GestureListener {

	@Override
	public boolean zoom(float originalDistance, float currentDistance) {
		Gdx.app.log(getClass().getSimpleName(), "zooming");
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialFirstPointer,
			Vector2 initialSecondPointer, Vector2 firstPointer,
			Vector2 secondPointer) {
		Gdx.app.log(getClass().getSimpleName(), "pinching");
		return false;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		Gdx.app.log(getClass().getSimpleName(), "touchDown");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.log(getClass().getSimpleName(), "tap");
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.log(getClass().getSimpleName(), "longPress");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.log(getClass().getSimpleName(), "fling");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.log(getClass().getSimpleName(), "pan");
		return false;
	}
}