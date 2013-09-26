package info.silin.gdxinit.entity;

import info.silin.gdxinit.Levels;

import com.badlogic.gdx.math.Vector2;

public class Weapon extends Entity {

	private float deltaSinceLastFired;
	public static final float COOLDOWN_TIME = 0.2f;
	public static final float PROJECTILE_VELOCITY = 7f;

	@Override
	public void update(float delta) {
		deltaSinceLastFired += delta;
	}

	public boolean canFire() {
		return deltaSinceLastFired > COOLDOWN_TIME;
	}

	public void shoot(Vector2 source, Vector2 target) {
		if (!canFire())
			return;
		Vector2 direction = target.sub(source).nor();
		deltaSinceLastFired = 0;
		Levels.getCurrent()
				.getProjectiles()
				.add(new Projectile(source, direction.mul(PROJECTILE_VELOCITY)));
	}
}
