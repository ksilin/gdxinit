package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.geo.Collider;

import java.util.List;

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

	public void draw(Camera cam, float delta) {
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.FilledRectangle);
		drawBlocks(delta);
		drawAvatar(delta);
		renderer.end();
	}

	private void drawAvatar(float delta) {
		renderer.setColor(AVATAR_COLOR);
		Rectangle rect = collider.predictBoundingBox(
				World.INSTANCE.getAvatar(), delta);
		renderer.filledRect(rect.x, rect.y, rect.width, rect.height);
	}

	private void drawBlocks(float delta) {
		List<Entity> collidingBlocks = collider.getCollidingBlocks(
				World.INSTANCE.getBlocksAroundAvatar(2), World.INSTANCE
						.getAvatar().getBoundingBox());
		for (Entity block : collidingBlocks) {
			drawBlock(block);
		}
	}

	private void drawBlock(Entity block) {
		Rectangle rect = block.getBoundingBox();
		renderer.setColor(BLOCK_COLOR);
		renderer.filledRect(rect.x, rect.y, rect.width, rect.height);
	}
}
