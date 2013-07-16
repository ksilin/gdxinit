package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;

import java.text.DecimalFormat;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DebugRenderer {

	private static final int MAGNIFICATION_FACTOR = 3;
	private static final Color BLOCK_COLOR = new Color(1, 0, 0, 1);
	private static final Color AVATAR_COLOR = new Color(0, 1, 0, 1);

	ShapeRenderer debugRenderer = new ShapeRenderer();
	FPSLogger fpsLogger = new FPSLogger();

	private World world;
	private RendererController rendererController;

	private DecimalFormat formatter = new DecimalFormat("#.##");
	private Label debugInfo;

	private int height;
	private int width;

	public DebugRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;

		debugInfo = new Label("debug label", rendererController.getSkin());
		debugInfo.setPosition(0, height / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);

		rendererController.getStage().addActor(debugInfo);
	}

	public void render(Camera cam) {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Rectangle);
		renderBlocks();
		renderAvatar();
		debugRenderer.end();

		renderAvatarVectors();

		debugInfo.setText(createInfoText());

		fpsLogger.log();
	}

	private StringBuilder createInfoText() {

		StringBuilder debugText = new StringBuilder("debug info: \n");
		Vector2 acceleration = world.getAvatar().getAcceleration();
		debugText.append(formatter.format(acceleration.x) + ", "
				+ formatter.format(acceleration.y) + "\n");
		Vector2 velocity = world.getAvatar().getVelocity();
		debugText.append(formatter.format(velocity.x) + ", "
				+ formatter.format(velocity.y) + "\n");
		return debugText;
	}

	private void renderAvatar() {
		Avatar avatar = world.getAvatar();
		Rectangle rect = avatar.getBoundingBox();
		debugRenderer.setColor(AVATAR_COLOR);
		debugRenderer.rect(rect.x, rect.y, rect.width, rect.height);

	}

	private void renderAvatarVectors() {

		Avatar avatar = world.getAvatar();
		Rectangle rect = avatar.getBoundingBox();

		debugRenderer.begin(ShapeType.Line);

		float centerX = rect.x + rect.width / 2;
		float centerY = rect.y + rect.height / 2;

		debugRenderer.setColor(AVATAR_COLOR);
		Vector2 velocity = avatar.getVelocity();
		debugRenderer.line(centerX, centerY, centerX + velocity.x
				* MAGNIFICATION_FACTOR, centerY + velocity.y
				* MAGNIFICATION_FACTOR);

		debugRenderer.setColor(new Color(0, 0, 1, 1));
		Vector2 acc = avatar.getAcceleration();
		debugRenderer.line(centerX, centerY, centerX + acc.x
				* MAGNIFICATION_FACTOR, centerY + acc.y * MAGNIFICATION_FACTOR);

		debugRenderer.end();
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

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
	}

}
