package info.silin.gdxinit;

import java.util.ArrayList;
import java.util.List;

import info.silin.gdxinit.entity.Block;

import com.badlogic.gdx.math.Vector2;

public class Level {

	private int width;
	private int height;
	private Block[][] blocks;

	public Level() {
		// TODO - this should be solved through inheritance or interface
		// implementation
		loadDemoLevel();
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

	public List<Block> getAllNonNullBlocks() {

		List<Block> result = new ArrayList<Block>();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks.length; j++) {

				if (blocks[i][j] != null) {
					result.add(blocks[i][j]);
				}
			}
		}
		return result;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}

	public Block getBlock(int x, int y) {
		return blocks[x][y];
	}

	private void loadDemoLevel() {
		width = 10;
		height = 7;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		for (int col = 0; col < 10; col++) {
			blocks[col][0] = new Block(new Vector2(col, 0));
			blocks[col][6] = new Block(new Vector2(col, 6));
			if (col > 2) {
				blocks[col][1] = new Block(new Vector2(col, 1));
			}
		}
		blocks[9][2] = new Block(new Vector2(9, 2));
		blocks[9][3] = new Block(new Vector2(9, 3));
		blocks[9][4] = new Block(new Vector2(9, 4));
		blocks[9][5] = new Block(new Vector2(9, 5));

		blocks[6][3] = new Block(new Vector2(6, 3));
		blocks[6][4] = new Block(new Vector2(6, 4));
		blocks[6][5] = new Block(new Vector2(6, 5));
	}

	private void prefillLevelWithNulls(int width, int height) {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				blocks[col][row] = null;
			}
		}
	}
}