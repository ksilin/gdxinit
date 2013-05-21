package info.silin.gdxinit.renderer;

import info.silin.gdxinit.Level;
import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Avatar;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class RendererController {

	private World world;

	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;

	DebugRenderer debugRenderer;
	TextRenderer textRenderer = new TextRenderer();
	DefaultRenderer defaultRenderer;
	CollisionRenderer collisionRenderer;

	private boolean debug = false;

	public void setSize(int w, int h) {
		debugRenderer.setSize(w, h);
		defaultRenderer.setSize(w, h);
		collisionRenderer.setSize(w, h);
	}

	public RendererController(World world, boolean debug) {

		this.world = world;
		this.debug = debug;

		defaultRenderer = new DefaultRenderer(world, this);
		debugRenderer = new DebugRenderer(world, this);
		collisionRenderer = new CollisionRenderer(world, this);
		setupCam();
	}

	private void setupCam() {
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH * 0.5f, CAMERA_HEIGHT * 0.5f, 0);
		this.cam.update();
	}

	public void render(float delta) {

		// defaultRenderer.render(cam);
		collisionRenderer.render(cam, delta);
		if (debug) {
			debugRenderer.render(cam);
		}
		// textRenderer.render();
	}

	public List<Block> getDrawableBlocks(int width, int height) {

		Avatar bob = world.getBob();
		Level level = world.getLevel();
		int levelWidth = level.getWidth();
		int levelHeight = level.getHeight();

		int left = Math.max((int) bob.getPosition().x - width, 0);
		int bottom = Math.max((int) bob.getPosition().y - height, 0);

		int right = Math.min(left + 2 * width, levelWidth - 1);
		int top = Math.min(bottom + 2 * height, levelHeight - 1);

		List<Block> blocks = new ArrayList<Block>();
		Block block;
		for (int col = left; col <= right; col++) {
			for (int row = bottom; row <= top; row++) {
				block = level.getBlocks()[col][row];
				if (block != null) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}
}
