package info.silin.gdxinit.entity;

import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Avatar {

	public enum State {
		IDLE, WALKING, DYING
	}

	static final float SPEED = 2f; // unit per second
	public static final float SIZE = 0.5f; // half a unit

	private Vector2 position = new Vector2();
	private Vector2 acceleration = new Vector2();
	private Vector2 velocity = new Vector2();
	private Rectangle bounds = new Rectangle();
	private State state = State.IDLE;
	private boolean facingLeft = true;

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	private float stateTime = 0;

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

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

	public Rectangle getBoundingBox() {
		Rectangle result = new Rectangle(bounds);
		result.x += position.x;
		result.y += position.y;
		return result;
	}

	public Polygon getPolygon() {
		return GeoFactory.fromRectangle(getBoundingBox());
	}

	public Avatar(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}
}