package info.silin.gdxinit.entity;

import info.silin.gdxinit.World;

import com.badlogic.gdx.math.Vector2;

public class Weapon extends Entity {

	private float deltaSinceLastFired;
	public static final float COOLDOWN_TIME = 0.3f;
	public static final float PROJECTILE_VELOCITY = 5f;

	@Override
	public void update(float delta) {
		deltaSinceLastFired += delta;
	}

	public boolean canFire() {
		return deltaSinceLastFired > COOLDOWN_TIME;
	}

	// TODO - replace dir by target -> better encapsulate precision
	// effects
	public void shoot(Vector2 source, Vector2 dir) {
		if (!canFire())
			return;

		deltaSinceLastFired = 0;
		World.INSTANCE.getProjectiles().add(
				new Projectile(source, dir.mul(PROJECTILE_VELOCITY)));
	}
}
