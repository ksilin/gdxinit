package info.silin.gdxinit.entity;

import info.silin.gdxinit.Levels;
import info.silin.gdxinit.audio.Sounds;

import com.badlogic.gdx.math.Vector2;

public class Weapon extends Entity {
	public static final float COOLDOWN_TIME = 0.1f;
	public static final float PROJECTILE_VELOCITY = 7f;

	private float deltaSinceLastFired;
	private float loadTime = 0.5f;
	private float loadTimeLeft = loadTime;
	private float cooldownTime = COOLDOWN_TIME;

	@Override
	public void update(float delta) {
		deltaSinceLastFired += delta;
	}

	public boolean canFire() {
		return deltaSinceLastFired > cooldownTime;
	}

	public void shoot(Vector2 source, Vector2 target) {
		if (!canFire())
			return;
		Vector2 direction = target.sub(source).nor();
		deltaSinceLastFired = 0;
		Levels.getCurrent()
				.getProjectiles()
				.add(new Projectile(source, direction.mul(PROJECTILE_VELOCITY)));
		Sounds.shot.play(0.5f);
	}

	public boolean isLoaded() {
		return loadTimeLeft < 0;
	}

	public void load(float delta) {
		if (loadTimeLeft == loadTime) {
			Sounds.reload.play();
		}
		loadTimeLeft -= delta;
	}

	public void unload() {
		loadTimeLeft = loadTime;
	}

	public float getCooldownTime() {
		return cooldownTime;
	}

	public void setCooldownTime(float cooldownTime) {
		this.cooldownTime = cooldownTime;
	}
}
