package info.silin.gdxinit.entity;

import com.badlogic.gdx.math.Vector2;

public class Vehicle extends Entity {

	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	protected float maxVelocity = 0f;
	protected float damp = 1f;
	protected float size = 1f;

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
		velocity.mul(damp);

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

	public float getDamp() {
		return damp;
	}

	public void setDamp(float damp) {
		this.damp = damp;
	}

}