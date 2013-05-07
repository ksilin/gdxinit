package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Block;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class DebugRenderer {

	private static final Color BLOCK_COLOR = new Color(1, 0, 0, 1);
	private static final Color AVATAR_COLOR = new Color(0, 1, 0, 1);

	ShapeRenderer debugRenderer = new ShapeRenderer();
	FPSLogger fpsLogger = new FPSLogger();

	private World world;
	private RendererController rendererController;

	private int height;
	private int width;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public DebugRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;
	}

	public void render(Camera cam) {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		renderBlocks();
		renderBob();
		debugRenderer.end();
		fpsLogger.log();
	}

	private void renderBob() {
		Rectangle rect = world.getBob().getBoundingBox();
		debugRenderer.setColor(AVATAR_COLOR);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
	}

	private void renderBlocks() {
		for (Block block : rendererController.getDrawableBlocks(width, height)) {
			renderBlock(block);
		}
	}

	private void renderBlock(Block block) {
		Rectangle rect = block.getBoundingBox();
		debugRenderer.setColor(BLOCK_COLOR);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);
	}
}
