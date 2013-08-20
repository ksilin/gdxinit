package info.silin.gdxinit.entity;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	public enum State {
		IDLE, PATROL, RUNNING, DYING
	}

	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	static final float SPEED = 2.5f;
	public static final float SIZE = 0.5f;
	private static final float ACCELERATION_FACTOR = 5f;

	private State state = State.IDLE;
	private boolean facingLeft = true;

	private float alertness = 0f;

	private Path patrolPath;
	private int currentPathIndex;

	private Weapon weapon = new Weapon();

	public Enemy(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
		state = State.PATROL;

		// if no patrol path given, create a stub one
		patrolPath = new Path();
		patrolPath.getWaypoints().add(position);
		currentPathIndex = 0;
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

		if (State.PATROL == state) {

			// have we reached the current waypoint?

			Vector2 waypoint = patrolPath.getWaypoints().get(currentPathIndex);
			Vector2 targetDir = waypoint.cpy().sub(position);

			if (targetDir.len2() < 0.2f) {

				currentPathIndex++;

				// the patrol path is iterated in a circle, so for a
				// back-and-forth movement, all waypoints have to be repeated in
				// the path
				if (currentPathIndex == patrolPath.getWaypoints().size()) {
					currentPathIndex = 0;
				}
				waypoint = patrolPath.getWaypoints().get(currentPathIndex);
				targetDir = waypoint.cpy().sub(position);
			}

			Vector2 targetAcc = targetDir.nor().mul(ACCELERATION_FACTOR);
			acceleration.add(targetAcc);
		}

		stateTime += delta;
		acceleration.mul(delta);

		velocity.add(acceleration.x, acceleration.y);
		velocity.mul(DAMP);

		constrainVelocity();
		Vector2 velocityPart = velocity.cpy().mul(delta);
		position.add(velocityPart);

		weapon.update(delta);
	}

	// TODO - common with all shooters - where to encapsulate?
	public void shoot(Vector2 target) {
		if (!weapon.canFire())
			return;

		Vector2 position = getBoundingBoxCenter();
		Vector2 direction = target.sub(position).nor();
		position.add(direction);
		weapon.shoot(position, direction);
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

	public Path getPatrolPath() {
		return patrolPath;
	}

	public void setPatrolPath(Path patrolPath) {
		this.patrolPath = patrolPath;
	}

	public int getCurrentPathIndex() {
		return currentPathIndex;
	}

	public void setCurrentPathIndex(int currentPathIndex) {
		this.currentPathIndex = currentPathIndex;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}