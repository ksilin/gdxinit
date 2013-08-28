package info.silin.gdxinit;

import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Path;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Level {

	private int width;
	private int height;
	private Block[][] blocks;
	private List<Enemy> initialEnemies = new ArrayList<Enemy>();
	private Enemy target;

	public Level() {
		loadDemoLevel();
	}

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

	private void loadDemoLevel() {
		width = 16;
		height = 10;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		blocks[4][4] = new Block(new Vector2(4, 4));
		blocks[5][5] = new Block(new Vector2(5, 5));
		blocks[6][6] = new Block(new Vector2(6, 6));

		for (int i = 0; i < 7; i++) {
			blocks[9][i] = new Block(new Vector2(9, i));
		}
		addBorders();

		addEnemies();

		addAssasinationTarget();
	}

	private void addAssasinationTarget() {
		target = new Enemy(new Vector2(13, 6));
	}

	private void addEnemies() {

		Enemy enemy1 = new Enemy(new Vector2(7, 7));
		enemy1.setPatrolPath(createPath1());
		initialEnemies.add(enemy1);

		Enemy enemy2 = new Enemy(new Vector2(8, 8));
		enemy2.setPatrolPath(createPath2());
		initialEnemies.add(enemy2);

	}

	private Path createPath1() {
		Path path = new Path();

		Vector2 waypoint1 = new Vector2(8, 8);
		Vector2 waypoint2 = new Vector2(11, 8);
		Vector2 waypoint3 = new Vector2(11, 3);
		Vector2 waypoint4 = new Vector2(14, 3);
		Vector2 waypoint5 = new Vector2(14, 8);

		List<Vector2> waypoints = path.getWaypoints();
		waypoints.add(waypoint1);
		waypoints.add(waypoint2);
		waypoints.add(waypoint3);
		waypoints.add(waypoint4);
		waypoints.add(waypoint5);
		waypoints.add(waypoint4);
		waypoints.add(waypoint3);
		waypoints.add(waypoint2);
		return path;
	}

	private Path createPath2() {
		Path path = new Path();

		Vector2 waypoint1 = new Vector2(8, 8);
		Vector2 waypoint2 = new Vector2(8, 2);
		Vector2 waypoint3 = new Vector2(2, 2);
		Vector2 waypoint4 = new Vector2(2, 8);

		List<Vector2> waypoints = path.getWaypoints();
		waypoints.add(waypoint1);
		waypoints.add(waypoint2);
		waypoints.add(waypoint3);
		waypoints.add(waypoint4);
		waypoints.add(waypoint1);
		return path;
	}

	private void prefillLevelWithNulls(int width, int height) {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}
	}

	private void addBorders() {
		for (int i = 0; i < width; i++) {
			blocks[i][0] = new Block(new Vector2(i, 0));
			blocks[i][height - 1] = new Block(new Vector2(i, height - 1));
		}
		for (int i = 0; i < height; i++) {
			blocks[0][i] = new Block(new Vector2(0, i));
			blocks[width - 1][i] = new Block(new Vector2(width - 1, i));
		}
	}

	public List<Enemy> getInitialEnemies() {
		return initialEnemies;
	}

	public void setInitialEnemies(List<Enemy> initialEnemies) {
		this.initialEnemies = initialEnemies;
	}

	public Enemy getTarget() {
		return target;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}
}