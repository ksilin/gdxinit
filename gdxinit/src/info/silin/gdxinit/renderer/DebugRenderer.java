package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;

import java.text.DecimalFormat;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DebugRenderer {

	private static final int MAGNIFICATION_FACTOR = 2;
	private static final Color BLOCK_COLOR = new Color(1, 0, 0, 1);
	private static final Color AVATAR_COLOR = new Color(0, 1, 0, 1);
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

	ShapeRenderer shapeRenderer = new ShapeRenderer();
	FPSLogger fpsLogger = new FPSLogger();

	private World world;
	private RendererController rendererController;

	TextRenderer textRenderer = new TextRenderer();
	GridRenderer gridRenderer = new GridRenderer();

	private DecimalFormat format = new DecimalFormat("#.##");
	private Label debugInfo;

	SpriteBatch fontBatch = new SpriteBatch();

	public DebugRenderer(World world, RendererController rendererController) {
		this.world = world;
		this.rendererController = rendererController;

		debugInfo = new Label("debug label", rendererController.getSkin());
		debugInfo.setPosition(0, Gdx.graphics.getHeight() / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);

		rendererController.getStage().addActor(debugInfo);
	}

	public void draw(Camera cam) {

		// TODO - blending does not belong here
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.identity();

		gridRenderer.drawGrid(cam);

		shapeRenderer.begin(ShapeType.Rectangle);
		drawBlocks();
		drawAvatar();
		shapeRenderer.end();

		gridRenderer.drawGridNumbers(cam);

		drawAvatarVectors();

		debugInfo.setText(createInfoText());

		drawProjectiles();

		Vector2 avatarPosition = world.getAvatar().getPosition();
		Vector3 projectedPos = new Vector3(avatarPosition.x, avatarPosition.y,
				1);

		// transform avatar position into screen coords
		cam.project(projectedPos);

		String newText = "pos: x: " + format.format(avatarPosition.x) + ", y: "
				+ format.format(avatarPosition.y);

		textRenderer.draw(newText, projectedPos.x, projectedPos.y);
	}

	private void drawProjectiles() {
		List<Projectile> projectiles = world.getProjectiles();

		shapeRenderer.begin(ShapeType.Rectangle);
		shapeRenderer.setColor(PROJECTILE_COLOR);
		for (Projectile p : projectiles) {
			Rectangle boundingBox = p.getBoundingBox();
			shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width,
					boundingBox.height);
		}
		shapeRenderer.end();
	}

	private StringBuilder createInfoText() {

		StringBuilder debugText = new StringBuilder("debug info: \n");
		Vector2 acceleration = world.getAvatar().getAcceleration();
		debugText.append(format.format(acceleration.x) + ", "
				+ format.format(acceleration.y) + "\n");
		Vector2 velocity = world.getAvatar().getVelocity();
		debugText.append(format.format(velocity.x) + ", "
				+ format.format(velocity.y) + "\n");
		debugText
				.append("shots alive: " + world.getProjectiles().size() + "\n");
		return debugText;
	}

	private void drawAvatar() {
		Entity avatar = world.getAvatar();
		Rectangle rect = avatar.getBoundingBox();
		shapeRenderer.setColor(AVATAR_COLOR);
		shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);

	}

	private void drawAvatarVectors() {

		Entity avatar = world.getAvatar();
		Rectangle rect = avatar.getBoundingBox();

		shapeRenderer.begin(ShapeType.Line);

		float centerX = rect.x + rect.width / 2;
		float centerY = rect.y + rect.height / 2;

		shapeRenderer.setColor(AVATAR_COLOR);
		Vector2 velocity = avatar.getVelocity();
		shapeRenderer.line(centerX, centerY, centerX + velocity.x
				* MAGNIFICATION_FACTOR, centerY + velocity.y
				* MAGNIFICATION_FACTOR);

		shapeRenderer.setColor(new Color(0, 0, 1, 1));
		Vector2 acc = avatar.getAcceleration();
		shapeRenderer.line(centerX, centerY, centerX + acc.x
				* MAGNIFICATION_FACTOR, centerY + acc.y * MAGNIFICATION_FACTOR);

		shapeRenderer.end();
	}

	private void drawBlocks() {
		for (Entity block : rendererController.getDrawableBlocks(
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight())) {
			drawBlock(block);
		}
	}

	private void drawBlock(Entity block) {
		Rectangle rect = block.getBoundingBox();
		shapeRenderer.setColor(BLOCK_COLOR);
		shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
	}
}
