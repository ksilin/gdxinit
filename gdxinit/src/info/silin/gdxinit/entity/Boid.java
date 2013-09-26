package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.steering.Steering;
import info.silin.gdxinit.events.Events;

import com.badlogic.gdx.math.Vector2;

public class Boid extends Vehicle {

	private static final float MAX_VEL = 5f;
	private static final float MAX_FORCE = 15f;
	public static final float SIZE = 0.5f;
	private static final float DAMP = 0.90f;
	private static final float MASS = 1.5f;

	private Vector2 targetPos;

	public Boid(Vector2 position) {
		this.position = position;
		this.targetPos = position;
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
		stateMachine.update(delta);

		Vector2 force = Steering.seek(targetPos, this);
		this.setForce(force);

		move(delta);
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
}