package info.silin.gdxinit.entity;

import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.geo.Collider;

import com.badlogic.gdx.math.Vector2;

public class Boid extends Vehicle {

	private static final float MAX_VEL = 5f;
	private static final float MAX_FORCE = 15f;
	public static final float SIZE = 0.5f;
	private static final float DAMP = 0.90f;
	private static final float MASS = 1f;

	private Vector2 targetPos;
	private Vector2 targetVelocity;

	public Boid(Vector2 position) {
		this.position = position;
		this.targetPos = position.cpy();
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
		this.damp = DAMP;
		this.maxVelocity = MAX_VEL;
		this.maxForce = MAX_FORCE;
		this.mass = MASS;
		Events.register(this);
	}

	public void update(float delta) {
		super.update(delta);
		Collider.pushBack(this, delta);
	}

	public void returnToPreviousState() {
		stateMachine.returnToPreviuosState();
	}

	public Vector2 getTargetPos() {
		return targetPos;
	}

	public void setTargetPos(Vector2 targetPos) {
		this.targetPos = targetPos;
	}

	public Vector2 getTargetVelocity() {
		return targetVelocity;
	}

	public void setTargetVelocity(Vector2 targetVelocity) {
		this.targetVelocity = targetVelocity;
	}
}