package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.Walking;
import info.silin.gdxinit.events.AvatarDeadEvent;
import info.silin.gdxinit.events.AvatarHitEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.geo.Collider;

import com.badlogic.gdx.math.Vector2;
import com.google.common.eventbus.Subscribe;

public class Avatar extends Vehicle {

	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	static final float SPEED = 2f; // unit per second
	public static final float MAX_FORCE = 20f;
	public static final float SIZE = 0.5f; // half a unit
	public static final float MASS = 0.5f; // half a unit

	private Weapon weapon;

	public Avatar(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
		this.damp = DAMP;
		this.maxVelocity = MAX_VEL;
		this.mass = MASS;
		this.setState(Walking.getInstance());
		Events.register(this);
	}

	@Subscribe
	public void onAvatarHitEvent(AvatarHitEvent event) {
		setState(Dead.getInstance());
		Events.post(new AvatarDeadEvent());
	}

	@Override
	public void update(float delta) {
		if (weapon != null)
			weapon.update(delta);
		super.update(delta);
		Collider.pushBack(this, delta);
	};

	public void shoot(Vector2 target) {
		if (null == weapon || !weapon.canFire())
			return;

		Vector2 position = getCenter();
		Vector2 direction = target.cpy().sub(position).nor();

		// shifting the shot source outside the enemy
		position.add(direction.mul(size));
		weapon.shoot(position, target);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
}