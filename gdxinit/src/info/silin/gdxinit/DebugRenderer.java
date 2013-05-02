package info.silin.gdxinit;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DebugRenderer {

	private static final Color BLOCK_COLOR = new Color(1, 0, 0, 1);
	private static final Color AVATAR_COLOR = new Color(0, 1, 0, 1);

	ShapeRenderer debugRenderer = new ShapeRenderer();

	private World world;

	private int height;
	private int width;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public DebugRenderer(World world) {
		this.world = world;
	}

	public void render(Camera cam) {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		renderBlocks();
		renderBob();
		debugRenderer.end();
	}

	private void renderBob() {
		Bob bob = world.getBob();
		Rectangle rect = bob.getBounds();
		float x1 = bob.getPosition().x + rect.x;
		float y1 = bob.getPosition().y + rect.y;
		debugRenderer.setColor(AVATAR_COLOR);
		debugRenderer.rect(x1, y1, rect.width, rect.height);
	}

	private void renderBlocks() {
		for (Block block : world.getDrawableBlocks(width, height)) {
			renderBlock(block);
		}
	}

	private void renderBlock(Block block) {
		Rectangle rect = block.getBounds();

		// TODO: why are we shifting here?
		float x1 = block.getPosition().x + rect.x;
		float y1 = block.getPosition().y + rect.y;

		debugRenderer.setColor(BLOCK_COLOR);
		debugRenderer.rect(x1, y1, rect.width, rect.height);
	}
}
