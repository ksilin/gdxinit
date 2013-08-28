package info.silin.gdxinit.renderer;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.GeoFactory;
import info.silin.gdxinit.util.SimpleFormat;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class DebugRenderer {

	private static final int VECTOR_MAGNIFICATION_FACTOR = 2;
	private static final Color BLOCK_COLOR = Color.CYAN;
	private static final Color AVATAR_COLOR = Color.GREEN;
	private static final Color ENEMY_COLOR = Color.RED;
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);

	private MyShapeRenderer shapeRenderer = new MyShapeRenderer();

	private TextRenderer textRenderer = new TextRenderer();
	private GridRenderer gridRenderer = new GridRenderer();

	// TODO - extract to DebugRenderOptions
	private boolean drawingEnemyVisibilityRanges = false;
	private boolean drawingPatrolPaths = false;
	private boolean drawingAvatarVectors = false;
	private boolean drawingShotRays = false;
	private boolean drawingMouse = false;
	private boolean drawingAvatarText = false;
	private boolean drawingGridNumbers = false;

	private Label debugInfo;

	public DebugRenderer() {
		debugInfo = createDebugInfo();
		RendererController.uiRenderer.stage.addActor(debugInfo);
	}

	private Label createDebugInfo() {
		debugInfo = new Label("debug label", RendererController.uiRenderer.skin);
		debugInfo.setPosition(0, Gdx.graphics.getHeight() / 2);
		debugInfo.setColor(0.8f, 0.8f, 0.2f, 1f);
		debugInfo.setSize(100, 100);
		return debugInfo;
	}

	public void draw(Camera cam) {

		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.identity();

		gridRenderer.drawGrid(cam);

		shapeRenderer.begin(ShapeType.Rectangle);
		drawBlocks();
		drawAvatar();
		drawEnemies();
		drawTarget();
		drawProjectiles();
		shapeRenderer.end();

		drawEnemyVisibilityRanges();

		shapeRenderer.begin(ShapeType.Line);
		drawPatrolPaths();
		drawAvatarVectors();
		drawShotRays();
		shapeRenderer.end();

		drawMouse(cam);

		gridRenderer.drawGridNumbers(cam);
		drawAvatarText(cam);

		debugInfo.setText(createInfoText());

		if (World.State.PAUSED == World.INSTANCE.getState()) {

			// TODO - for some reason I have to enable blending again -
			// reproduce and fix the issue
			Gdx.gl.glEnable(GL10.GL_BLEND);
			Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.setColor(0.1f, 0.1f, 0.1f, 0.5f);
			// TODO - replace the numbers with correct ones
			shapeRenderer.filledRect(0, 0, RendererController.CAMERA_WIDTH,
					RendererController.CAMERA_WIDTH);
			shapeRenderer.end();
		}
	}

	private void drawEnemyVisibilityRanges() {

		if (!drawingEnemyVisibilityRanges)
			return;

		shapeRenderer.begin(ShapeType.Line);

		Vector2 avatarCenter = World.INSTANCE.getAvatar()
				.getBoundingBoxCenter();
		for (Enemy e : World.INSTANCE.getEnemies()) {
			if (e.getState() != Enemy.State.DYING) {

				Polygon viewRayPoly = GeoFactory.fromSegment(
						e.getBoundingBoxCenter(), avatarCenter);

				shapeRenderer.drawPolygon(viewRayPoly.getVertices());
			}
		}
		shapeRenderer.end();
	}

	private void drawBlocks() {
		for (Entity block : World.INSTANCE.getBlocksAroundAvatar(10)) {
			shapeRenderer.drawRect(block.getBoundingBox(), BLOCK_COLOR);
		}
	}

	private void drawAvatar() {
		Entity avatar = World.INSTANCE.getAvatar();
		shapeRenderer.drawRect(avatar.getBoundingBox(), AVATAR_COLOR);
	}

	private void drawEnemies() {

		for (Enemy e : World.INSTANCE.getEnemies()) {
			if (e.getState() != Enemy.State.DYING) {
				shapeRenderer.drawRect(e.getBoundingBox(), ENEMY_COLOR);
			}
		}
	}

	private void drawTarget() {
		Enemy target = World.INSTANCE.getLevel().getTarget();
		if (target.getState() != Enemy.State.DYING) {
			shapeRenderer.drawRect(target.getBoundingBox(), Color.LIGHT_GRAY);
		}
	}

	private void drawPatrolPaths() {
		if (!drawingPatrolPaths)
			return;
		for (Enemy e : World.INSTANCE.getEnemies()) {
			if (Enemy.State.PATROL == e.getState()) {

				List<Vector2> waypoints = e.getPatrolPath().getWaypoints();
				for (int i = 0; i < waypoints.size() - 1; i++) {
					shapeRenderer.drawLine(waypoints.get(i),
							waypoints.get(i + 1), ENEMY_COLOR);
				}
			}
		}
	}

	private void drawMouse(Camera cam) {
		if (!drawingMouse)
			return;
		shapeRenderer.begin(ShapeType.Circle);
		Vector2 mousePos = RendererController.getUnprojectedMousePosition();
		shapeRenderer.circle(mousePos.x, mousePos.y, 0.2f, 10);
		shapeRenderer.end();
	}

	private void drawAvatarVectors() {
		if (!drawingAvatarVectors)
			return;
		Entity avatar = World.INSTANCE.getAvatar();
		Vector2 center = avatar.getBoundingBoxCenter();

		Vector2 velocity = avatar.getVelocity().cpy()
				.mul(VECTOR_MAGNIFICATION_FACTOR);
		shapeRenderer.drawLineRelative(center, velocity, AVATAR_COLOR);
		shapeRenderer.setColor(Color.BLUE);
		Vector2 acc = avatar.getAcceleration().cpy()
				.mul(VECTOR_MAGNIFICATION_FACTOR);
		shapeRenderer.drawLineRelative(center, acc, Color.BLUE);
	}

	private StringBuilder createInfoText() {

		StringBuilder debugText = new StringBuilder("debug info: \n");
		Avatar avatar = World.INSTANCE.getAvatar();
		Vector2 acceleration = avatar.getAcceleration();
		debugText.append(SimpleFormat.format(acceleration.x) + ", "
				+ SimpleFormat.format(acceleration.y) + "\n");
		Vector2 velocity = avatar.getVelocity();
		debugText.append(SimpleFormat.format(velocity.x) + ", "
				+ SimpleFormat.format(velocity.y) + "\n");
		debugText.append("shots alive: "
				+ World.INSTANCE.getProjectiles().size() + "\n");
		return debugText;
	}

	private void drawShotRays() {
		if (!drawingShotRays)
			return;
		// TODO - mixing V3 and V2 - how to deal with it properly?
		for (Ray ray : World.INSTANCE.getShotRays()) {
			Vector3 origin = ray.origin;
			Vector3 direction = ray.direction.cpy().mul(10);

			shapeRenderer.drawLineRelative(new Vector2(origin.x, origin.y),
					new Vector2(direction.x, direction.y), Color.YELLOW);
		}
	}

	private void drawProjectiles() {
		for (Projectile p : World.INSTANCE.getProjectiles()) {
			shapeRenderer.drawRect(p.getBoundingBox(), PROJECTILE_COLOR);
		}
	}

	private void drawAvatarText(Camera cam) {
		if (!drawingAvatarText)
			return;
		Vector2 avatarPosition = World.INSTANCE.getAvatar().getPosition();
		Vector3 projectedPos = new Vector3(avatarPosition.x, avatarPosition.y,
				1);
		// transform avatar position into screen coords
		cam.project(projectedPos);

		String newText = "pos: x: " + SimpleFormat.format(avatarPosition.x)
				+ ", y: " + SimpleFormat.format(avatarPosition.y);
		textRenderer.draw(newText, projectedPos.x, projectedPos.y);
	}

	public boolean isDrawingEnemyVisibilityRanges() {
		return drawingEnemyVisibilityRanges;
	}

	public void setDrawingEnemyVisibilityRanges(
			boolean drawingEnemyVisibilityRanges) {
		this.drawingEnemyVisibilityRanges = drawingEnemyVisibilityRanges;
	}

	public boolean isDrawingPatrolPaths() {
		return drawingPatrolPaths;
	}

	public void setDrawingPatrolPaths(boolean drawingPatrolPaths) {
		this.drawingPatrolPaths = drawingPatrolPaths;
	}

	public boolean isDrawingAvatarVectors() {
		return drawingAvatarVectors;
	}

	public void setDrawingAvatarVectors(boolean drawingAvatarVectors) {
		this.drawingAvatarVectors = drawingAvatarVectors;
	}

	public boolean isDrawingShotRays() {
		return drawingShotRays;
	}

	public void setDrawingShotRays(boolean drawingShotRays) {
		this.drawingShotRays = drawingShotRays;
	}

	public boolean isDrawingMouse() {
		return drawingMouse;
	}

	public void setDrawingMouse(boolean drawingMouse) {
		this.drawingMouse = drawingMouse;
	}

	public boolean isDrawingAvatarText() {
		return drawingAvatarText;
	}

	public void setDrawingAvatarText(boolean drawingAvatarText) {
		this.drawingAvatarText = drawingAvatarText;
	}

	public boolean isDrawingGridNumbers() {
		return drawingGridNumbers;
	}

	public void setDrawingGridNumbers(boolean drawingGridNumbers) {
		this.drawingGridNumbers = drawingGridNumbers;
	}
}
