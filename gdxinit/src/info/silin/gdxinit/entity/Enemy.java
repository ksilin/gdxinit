package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.enemy.KillableByAvatarTouch;
import info.silin.gdxinit.entity.state.enemy.Patrol;
import info.silin.gdxinit.entity.state.enemy.ShootAvatarOnSight;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.VehicleHitEvent;

import com.badlogic.gdx.math.Vector2;
import com.google.common.eventbus.Subscribe;

public class Enemy extends Vehicle {

	private static final float MAX_VEL = 5f;
	private static final float MAX_FORCE = 15f;
	public static final float SIZE = 0.5f;
	private static final float DAMP = 0.90f;
	private static final float MASS = 1.5f;

	private static float MEMORY_DURATION = 3f;
	private float timeSinceSeenAvatar = 0;
	private float timeSeeingAvatar = 0;

	private float alertness = 0f;

	private Path patrolPath;

	private Weapon weapon = new Weapon();
	private Vector2 lastAvatarPosition;
	private EnemyVision vision;

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
		Events.register(this);
		vision = new EnemyVision(this);
	}

	public void update(float delta) {
		vision.update(delta);
		stateMachine.update(delta);

		if (weapon != null) {
			weapon.update(delta);
			if (forgotAvatar()) {
				weapon.unload();
			}
		}
	}

	@Subscribe
	public void onHit(VehicleHitEvent event) {
		if (this == event.getVehicle()) {
			setState(Dead.getInstance());
			stateMachine.removeGlobalState(ShootAvatarOnSight.getInstance());
			// stateMachine.removeGlobalState(KillableByAvatarTouch.getInstance());
		}
	}

	public void shoot(Vector2 target, float delta) {
		if (null == weapon || !weapon.canFire())
			return;

		if (!weapon.isLoaded()) {
			weapon.load(delta);
			return;
		}

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

	public void returnToPreviousState() {
		stateMachine.returnToPreviuosState();
	}

	public EnemyVision getVision() {
		return vision;
	}

	public float getTimeSeeingAvatar() {
		return timeSeeingAvatar;
	}

	public void setTimeSeeingAvatar(float timeSeeingAvatar) {
		this.timeSeeingAvatar = timeSeeingAvatar;
	}
}