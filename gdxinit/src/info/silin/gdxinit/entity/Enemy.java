package info.silin.gdxinit.entity;

import java.util.List;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.state.Patrol;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	static final float SPEED = 2.5f;
	public static final float SIZE = 0.5f;
	public static final float ACCELERATION_FACTOR = 5f;

	// private State state = State.IDLE;
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
		this.maxVelocity = MAX_VEL;
		setState(Patrol.getINSTANCE());

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
		return stateMachine.getCurrentState();
	}

	public void setState(State state) {
		stateMachine.setState(state);
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void update(float delta) {

		stateMachine.update(delta);
		move(delta);
		weapon.update(delta);
	}

	private void move(float delta) {
		acceleration.mul(delta);

		velocity.add(acceleration.x, acceleration.y);
		velocity.mul(DAMP);

		constrainVelocity();
		Vector2 velocityPart = velocity.cpy().mul(delta);
		position.add(velocityPart);
	}

	public boolean canSeeAvatar() {
		Avatar avatar = World.INSTANCE.getAvatar();
		Polygon viewRay = GeoFactory.fromSegment(getBoundingBoxCenter(),
				avatar.getBoundingBoxCenter());

		List<Entity> nonNullBlocks = World.INSTANCE.getLevel()
				.getNonNullBlocks();

		List<Entity> collidingEntities = Collider.getCollidingEntities(
				nonNullBlocks, viewRay);

		return collidingEntities.isEmpty();
	}

	// TODO - common with all shooters - where to encapsulate?
	public void shoot(Vector2 target) {
		if (!weapon.canFire())
			return;

		Vector2 position = getBoundingBoxCenter();
		Vector2 direction = target.cpy().sub(position).nor();

		// shifting the shot source outside the enemy, so no collisions are
		// triggered
		position.add(direction.mul(size));
		weapon.shoot(position, target);
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