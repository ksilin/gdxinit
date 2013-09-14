package info.silin.gdxinit.entity;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.state.KillableByAvatarTouch;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.state.enemy.Patrol;
import info.silin.gdxinit.entity.state.enemy.ShootAvatarOnSight;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.GeoFactory;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Vehicle {

	private static final float MAX_VEL = 4f;
	private static final float MAX_FORCE = 15f;
	public static final float SIZE = 0.5f;
	private static final float DAMP = 0.90f;
	private static final float MASS = 2f;

	private static float MEMORY_DURATION = 1f;
	private float timeSinceSeenAvatar = 0;
	private float maxVisionDistance = 5f;
	private double viewAngleCos = 0.75; // 30deg to each side = 60deg

	// TODO - parking vision here for now - not sure how to encapsulate it
	// properly
	private Vector2 viewDir = new Vector2();
	private float maxViewRotationSpeed = 360; // degrees per second

	private float alertness = 0f;

	private Path patrolPath;
	// private int currentPathIndex;

	private Weapon weapon = new Weapon();
	private Vector2 lastAvatarPosition;

	public Enemy(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
		this.damp = DAMP;
		this.maxVelocity = MAX_VEL;
		this.maxForce = MAX_FORCE;
		this.mass = MASS;
		setState(Patrol.getInstance());
		stateMachine.addGlobalState(KillableByAvatarTouch.getInstance());
		stateMachine.addGlobalState(ShootAvatarOnSight.getInstance());
		// if no patrol path given, create a stub one
		patrolPath = new Path();
		patrolPath.getWaypoints().add(position);
	}

	public void update(float delta) {
		see(delta);
		stateMachine.update(delta);
		if (weapon != null)
			weapon.update(delta);
	}

	// TODO - perhaps better as a global State
	public void see(float delta) {
		Avatar avatar = World.INSTANCE.getAvatar();

		if (canSeeAvatar(avatar)) {
			Gdx.app.log("Enemy", "seeing avatar ");
			setLastAvatarPosition(World.INSTANCE.getAvatar().getPosition());
			setTimeSinceSeenAvatar(0);
			return;
		}
		setTimeSinceSeenAvatar(timeSinceSeenAvatar + delta);
	}

	private boolean canSeeAvatar(Avatar avatar) {
		return isAvatarCloseEnough(avatar) && isAvatarInsideViewAngle(avatar)
				&& !isAvatarBehindObstacle(avatar);
	}

	private boolean isAvatarBehindObstacle(Avatar avatar) {
		Polygon viewRay = GeoFactory.fromSegment(getCenter(),
				avatar.getCenter());
		List<Entity> nonNullBlocks = World.INSTANCE.getLevel()
				.getNonNullBlocks();

		List<Entity> collidingEntities = Collider.getCollidingEntities(
				nonNullBlocks, viewRay);
		boolean behindObstacle = !collidingEntities.isEmpty();
		return behindObstacle;
	}

	private boolean isAvatarInsideViewAngle(Avatar avatar) {
		Vector2 dir = avatar.getCenter().sub(getCenter()).nor();
		float cos = dir.dot(viewDir.cpy().nor());
		return cos > viewAngleCos;
	}

	public boolean isAvatarCloseEnough(Avatar avatar) {
		Vector2 dir = avatar.getCenter().sub(getCenter());
		return dir.len2() < maxVisionDistance * maxVisionDistance;
	}

	public void shoot(Vector2 target) {
		if (null == weapon || !weapon.canFire())
			return;

		Vector2 position = getCenter();
		Vector2 direction = target.cpy().sub(position).nor();

		// shifting the shot source outside the enemy
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

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void setLastAvatarPosition(Vector2 pos) {
		lastAvatarPosition = pos;
	}

	public Vector2 getLastAvatarPosition() {
		return lastAvatarPosition;
	}

	public float getTimeSinceSeenAvatar() {
		return timeSinceSeenAvatar;
	}

	public void setTimeSinceSeenAvatar(float timeSinceSeenAvatar) {
		this.timeSinceSeenAvatar = timeSinceSeenAvatar;
	}

	public boolean forgotAvatar() {
		return timeSinceSeenAvatar > MEMORY_DURATION;
	}

	public boolean canSeeAvatar() {
		return timeSinceSeenAvatar == 0;
	}

	public float getMaxVisionDistance() {
		return maxVisionDistance;
	}

	public void setMaxVisionDistance(float maxVisionDistance) {
		this.maxVisionDistance = maxVisionDistance;
	}

	public double getViewAngleCos() {
		return viewAngleCos;
	}

	public void setViewAngleCos(double viewAngleCos) {
		this.viewAngleCos = viewAngleCos;
	}

	public Vector2 getViewDir() {
		return viewDir;
	}

	public void setViewDir(Vector2 viewDir) {
		this.viewDir = viewDir;
	}

	public void addGlobalState(State state) {
		stateMachine.addGlobalState(state);
	}

	public void removeGlobalState(State state) {
		stateMachine.removeGlobalState(state);
	}

	public void returnToPreviousState() {
		stateMachine.returnToPreviuosState();
	}
}