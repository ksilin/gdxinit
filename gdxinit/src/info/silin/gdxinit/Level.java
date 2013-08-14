package info.silin.gdxinit;

import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Level {

	private int width;
	private int height;
	private Block[][] blocks;
	private List<Enemy> initialEnemies = new ArrayList<Enemy>();

	public Level() {
		// TODO - this should be solved through inheritance or interface
		// implementation
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
	}

	private void addEnemies() {

		Enemy enemy = new Enemy(new Vector2(7, 7));
		initialEnemies.add(enemy);
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
}