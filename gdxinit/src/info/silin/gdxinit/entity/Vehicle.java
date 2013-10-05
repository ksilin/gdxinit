package info.silin.gdxinit.entity;

import info.silin.gdxinit.geo.GeoUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.Sets.SetView;

public class Vehicle extends Entity {

	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	protected float maxVelocity = 0f;
	protected float damp = 1f;
	protected float size = 1f;

	protected float mass = 1f;
	protected Vector2 force = new Vector2();
	protected float maxForce = 10f;
	protected float maxTurnRate = 10f; // yet unused

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	@Override
	public void update(float delta) {
		Gdx.app.log("Vehicle", "updating");
		super.update(delta);
		calcAcceleration(delta);
		acceleration.mul(delta);
		velocity.add(acceleration.x, acceleration.y);
		velocity.mul(damp);
		if (velocity.len2() > 0.0001f) {
			GeoUtils.constrain(velocity, maxVelocity);
			Vector2 velocityPart = velocity.cpy().mul(delta);
			position.add(velocityPart);
		}
	}

	private void calcAcceleration(float delta) {
		acceleration = force.cpy().div(mass);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
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

	public Vector2 getForce() {
		return force;
	}

	public void setForce(Vector2 force) {
		this.force = force;
	}

	public float getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(float maxForce) {
		this.maxForce = maxForce;
	}

	public float getMaxTurnRate() {
		return maxTurnRate;
	}

	public void setMaxTurnRate(float maxTurnRate) {
		this.maxTurnRate = maxTurnRate;
	}

	public void stop() {
		force = Vector2.Zero;
		acceleration = Vector2.Zero;
		velocity = Vector2.Zero;
	}

}