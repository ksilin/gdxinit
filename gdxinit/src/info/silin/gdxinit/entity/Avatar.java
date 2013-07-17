package info.silin.gdxinit.entity;

import com.badlogic.gdx.math.Vector2;

public class Avatar extends Entity {

	public enum State {
		IDLE, WALKING, DYING
	}

	static final float SPEED = 2f; // unit per second
	public static final float SIZE = 0.5f; // half a unit

	private State state = State.IDLE;
	private boolean facingLeft = true;

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	private float stateTime = 0;

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void update(float delta) {
		stateTime += delta;
		Vector2 velocityPart = velocity.cpy().mul(delta);
		position.add(velocityPart);
	}

	public Avatar(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
}