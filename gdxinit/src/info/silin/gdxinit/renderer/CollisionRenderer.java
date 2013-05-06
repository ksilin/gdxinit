package info.silin.gdxinit.renderer;

import java.util.List;

import info.silin.gdxinit.Collider;
import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class CollisionRenderer {

	private static final Color BLOCK_COLOR = new Color(1, 0, 0, 1);
	private static final Color AVATAR_COLOR = new Color(0, 1, 0, 1);

	private ShapeRenderer renderer = new ShapeRenderer();

	private Collider collider = new Collider();

	private World world;

	private int height;
	private int width;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public CollisionRenderer(World world) {
		this.world = world;
	}

	public void render(Camera cam, float delta) {
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.FilledRectangle);
		renderBob(delta);
		renderBlocks(delta);
		renderer.end();
	}

	private void renderBob(float delta) {
		renderer.setColor(AVATAR_COLOR);
		Rectangle rect = collider.createNextFrameBB(world.getBob(), delta);
		Gdx.app.log("next frame rect: ", rect.x + ", " + rect.y + ", "
				+ rect.width + ", " + rect.height);
		renderer.filledRect(rect.x, rect.y, rect.width, rect.height);
	}

	private void renderBlocks(float delta) {
		List<Block> collidingBlocks = collider.getCollidingBlocks(
				world.getDrawableBlocks(width, height), world.getBob(), delta);
		Gdx.app.log("colliding blocks count: ", "" + collidingBlocks.size());
		for (Block block : collidingBlocks) {
			renderBlock(block);
		}
	}

	private void renderBlock(Block block) {
		Rectangle rect = block.getBounds();

		float x1 = block.getPosition().x + rect.x;
		float y1 = block.getPosition().y + rect.y;

		renderer.setColor(BLOCK_COLOR);
		renderer.filledRect(x1, y1, rect.width, rect.height);
	}
}
