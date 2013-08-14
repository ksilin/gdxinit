package info.silin.gdxinit.entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	public enum State {
		IDLE, WALKING, RUNNING, DYING
	}

	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	static final float SPEED = 2f; // unit per second
	public static final float SIZE = 0.5f; // half a unit

	private State state = State.IDLE;
	private boolean facingLeft = true;

	private float alertness = 0f;

	public Enemy(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
	}

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
		acceleration.mul(delta);

		velocity.add(acceleration.x, acceleration.y);
		velocity.mul(DAMP);

		constrainVelocity();
		Vector2 velocityPart = velocity.cpy().mul(delta);
		position.add(velocityPart);
	}

	private void constrainVelocity() {
		if (velocity.x > MAX_VEL) {
			velocity.x = MAX_VEL;
		}
		if (velocity.x < -MAX_VEL) {
			velocity.x = -MAX_VEL;
		}

		if (velocity.y > MAX_VEL) {
			velocity.y = MAX_VEL;
		}
		if (velocity.y < -MAX_VEL) {
			velocity.y = -MAX_VEL;
		}
	}

	public float getAlertness() {
		return alertness;
	}

	public void setAlertness(float alertness) {
		this.alertness = alertness;
	}
}