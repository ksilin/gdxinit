package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.Collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public enum World {

	INSTANCE;

	private Avatar avatar;
	private Level level;

	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Explosion> explosions = new ArrayList<Explosion>();
	private List<Collision> collisions = new ArrayList<Collision>();

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

	public List<Entity> getDrawableBlocks(int width, int height) {

		int levelWidth = level.getWidth();
		int levelHeight = level.getHeight();

		int left = Math.max((int) avatar.getPosition().x - width, 0);
		int bottom = Math.max((int) avatar.getPosition().y - height, 0);

		int right = Math.min(left + 2 * width, levelWidth - 1);
		int top = Math.min(bottom + 2 * height, levelHeight - 1);

		List<Entity> blocks = new ArrayList<Entity>();
		Block block;
		for (int column = left; column <= right; column++) {
			for (int row = bottom; row <= top; row++) {
				block = level.getBlocks()[column][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}
}
