package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.Collision;

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

	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Explosion> explosions = new ArrayList<Explosion>();
	private List<Collision> collisions = new ArrayList<Collision>();
	private List<Ray> shotRays = new ArrayList<Ray>();

	private World() {
		// TODO - this should be solved through inheritance or interface
		// implementation
		createDemoWorld();
	}

	private void createDemoWorld() {
		avatar = new Avatar(new Vector2(2, 3));
		level = new Level();
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public Level getLevel() {
		return level;
	}

	public List<Collision> getCollisions() {
		return collisions;
	}

	public void setCollisions(List<Collision> collisions) {
		this.collisions = collisions;
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
}
