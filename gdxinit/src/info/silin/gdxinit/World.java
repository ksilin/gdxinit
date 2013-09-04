package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public enum World {

	INSTANCE;

	public static final float WIDTH = 16f;
	public static final float HEIGHT = 10f;

	private Avatar avatar;
	private Level level;

	private List<Projectile> projectiles;
	private List<Enemy> enemies;
	private List<Explosion> explosions;
	private List<Ray> shotRays;

	private State state = State.RUNNING;

	public enum State {
		RUNNING, PAUSED
	}

	private World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		projectiles = new ArrayList<Projectile>();
		enemies = new ArrayList<Enemy>();
		explosions = new ArrayList<Explosion>();
		shotRays = new ArrayList<Ray>();
		avatar = new Avatar(new Vector2(2, 3));
		level = new Level();
		enemies = level.getInitialEnemies();
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public Level getLevel() {
		return level;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(List<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public List<Explosion> getExplosions() {
		return explosions;
	}

	public void setExplosions(List<Explosion> explosions) {
		this.explosions = explosions;
	}

	public List<Ray> getShotRays() {
		return shotRays;
	}

	public void setShotRays(List<Ray> shotRays) {
		this.shotRays = shotRays;
	}

	public List<Entity> getBlocksAroundAvatar(int radius) {
		return level.getBlocksAround(avatar, radius);
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void restartCurrentLevel() {
		createDemoWorld();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Entity> getAllBlocks() {
		return level.getNonNullBlocks();
	}
}
