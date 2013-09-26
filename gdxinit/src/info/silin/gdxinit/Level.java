package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public abstract class Level {

	protected int width;
	protected int height;
	protected Block[][] blocks;
	protected List<Enemy> enemies;
	protected Enemy target;
	protected Avatar avatar;

	protected List<Projectile> projectiles;
	protected List<Explosion> explosions;
	protected List<Ray> shotRays;

	public abstract void init();

	public List<Entity> getNonNullBlocks() {

		List<Entity> result = new ArrayList<Entity>();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {

				if (blocks[i][j] != null) {
					result.add(blocks[i][j]);
				}
			}
		}
		return result;
	}

	public List<Entity> getBlocksAround(Entity entity, int radius) {

		Vector2 position = entity.getPosition();
		int left = Math.max((int) position.x - radius, 0);
		int bottom = Math.max((int) position.y - radius, 0);

		int right = Math.min(left + 2 * radius, width - 1);
		int top = Math.min(bottom + 2 * radius, height - 1);

		List<Entity> result = new ArrayList<Entity>();
		Block block;
		for (int column = left; column <= right; column++) {
			for (int row = bottom; row <= top; row++) {
				block = blocks[column][row];
				if (block != null) {
					result.add(block);
				}
			}
		}
		return result;
	}

	public List<Entity> getBlocksInArea(int left, int right, int top, int bottom) {
		List<Entity> result = new ArrayList<Entity>();
		Block block;
		for (int column = left; column <= right; column++) {
			for (int row = bottom; row <= top; row++) {
				block = this.blocks[column][row];
				if (block != null) {
					result.add(block);
				}
			}
		}
		return result;
	}

	protected void prefillLevelWithNulls(int width, int height) {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}
	}

	protected void addBorders() {
		for (int i = 0; i < width; i++) {
			blocks[i][0] = new Block(new Vector2(i, 0));
			blocks[i][height - 1] = new Block(new Vector2(i, height - 1));
		}
		for (int i = 0; i < height; i++) {
			blocks[0][i] = new Block(new Vector2(0, i));
			blocks[width - 1][i] = new Block(new Vector2(width - 1, i));
		}
	}

	protected void fillFromTo(int fromX, int fromY, int toX, int toY) {
		for (int i = fromX; i <= toX; i++) {
			for (int j = fromY; j <= toY; j++) {
				blocks[i][j] = new Block(new Vector2(i, j));
			}
		}
	}

	public List<Entity> getBlocksAroundAvatar(int radius) {
		return getBlocksAround(avatar, radius);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Block[][] getBlocks() {
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Block getBlock(int x, int y) {
		return blocks[x][y];
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public Enemy getTarget() {
		return target;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public Avatar getAvatar() {
		return avatar;
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
}