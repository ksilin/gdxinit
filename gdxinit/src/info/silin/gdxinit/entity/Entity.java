package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.state.StateMachine;
import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {

	protected Vector2 position = new Vector2();
	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	protected float maxVelocity = 0;
	protected Rectangle bounds = new Rectangle();
	protected float size = 1f;
	protected StateMachine stateMachine = new StateMachine();
	private static final float DAMP = 0.90f;

	public Entity() {
		super();
		stateMachine.setOwner(this);
	}

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

	public void move(float delta) {
		Vector2 accelerationPart = acceleration.mul(delta);

		velocity.add(accelerationPart.x, accelerationPart.y);
		velocity.mul(DAMP);

		constrainVelocity();
		Vector2 velocityPart = velocity.cpy().mul(delta);
		position.add(velocityPart);
	}

	protected void constrainVelocity() {
		if (velocity.x > maxVelocity) {
			velocity.x = maxVelocity;
		}
		if (velocity.x < -maxVelocity) {
			velocity.x = -maxVelocity;
		}

		if (velocity.y > maxVelocity) {
			velocity.y = maxVelocity;
		}
		if (velocity.y < -maxVelocity) {
			velocity.y = -maxVelocity;
		}
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

	public Vector2 getBoundingBoxCenter() {
		return new Vector2(position.x + size / 2f, position.y + size / 2f);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void update(float delta) {
		stateMachine.update(delta);
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

	public State getState() {
		return stateMachine.getCurrentState();
	}

	public void setState(State state) {
		stateMachine.setState(state);
	}

}