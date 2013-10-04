package info.silin.gdxinit.renderer;

import info.silin.gdxinit.Levels;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.EnemyVision;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.enemy.Patrol;
import info.silin.gdxinit.util.SimpleFormat;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DebugRenderer {

	private static final int VECTOR_MAGNIFICATION_FACTOR = 2;
	private static final Color BLOCK_COLOR = Color.CYAN;
	private static final Color AVATAR_COLOR = Color.GREEN;
	private static final Color ENEMY_COLOR = Color.RED;
	private static final Color PROJECTILE_COLOR = new Color(0.8f, 0.8f, 0, 1);
	private static final Color VIEWFIELD_COLOR = new Color(1, 1, 1, 0.2f);

	private MyShapeRenderer shapeRenderer = new MyShapeRenderer();

	private TextRenderer textRenderer = new TextRenderer();
	private GridRenderer gridRenderer = new GridRenderer();

	private boolean drawingEnemyVisibilityRanges = true;
	private boolean drawingPatrolPaths = false;
	private boolean drawingAvatarVectors = false;
	private boolean drawingMouse = false;
	private boolean drawingAvatarText = false;
	private boolean drawingGridNumbers = false;

	public void draw(Camera cam) {

		shapeRenderer.setProjectionMatrix(cam.combined);

		gridRenderer.drawGrid(cam);

		shapeRenderer.begin(ShapeType.Rectangle);
		drawBlocks();
		drawAvatar();
		drawTarget();
		drawProjectiles();
		drawEnemies();
		// TODO - abomination!
		if (Levels.STEERING_LAB == Levels.getCurrent()) {
			Boid boid = Levels.STEERING_LAB.getBoid();
			shapeRenderer.drawRect(boid.getBoundingBox(), PROJECTILE_COLOR);

			Vector2 targetPos = boid.getTargetPos();

			shapeRenderer.rect(targetPos.x - 0.3f, targetPos.y - 0.3f, 0.3f,
					0.3f);
		}
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		decorateDeadEnemies();
		drawEnemyViewFields();
		drawPatrolPaths();
		drawAvatarVectors();
		shapeRenderer.end();

		drawMouse(cam);

		gridRenderer.drawGridNumbers(cam);
		drawAvatarText(cam);

		RendererController.uiRenderer.getDebugInfo().setText(createInfoText());
	}

	private void drawEnemyViewFields() {

		if (!drawingEnemyVisibilityRanges)
			return;

		for (Enemy e : Levels.getCurrent().getEnemies()) {
			if (Dead.getInstance() != e.getState())
				drawViewField(e);
		}
		drawViewField(Levels.getCurrent().getTarget());
	}

	private void drawBlocks() {
		for (Entity block : Levels.getCurrent().getNonNullBlocks()) {
			shapeRenderer.drawRect(block.getBoundingBox(), BLOCK_COLOR);
		}
	}

	private void drawAvatar() {
		Entity avatar = Levels.getCurrent().getAvatar();
		shapeRenderer.drawRect(avatar.getBoundingBox(), AVATAR_COLOR);
	}

	private void drawEnemies() {
		for (Enemy e : Levels.getCurrent().getEnemies()) {
			Rectangle boundingBox = e.getBoundingBox();
			shapeRenderer.drawRect(boundingBox, ENEMY_COLOR);
			if (Dead.getInstance() == e.getState()) {

			}
		}
	}

	private void decorateDeadEnemies() {
		for (Enemy e : Levels.getCurrent().getEnemies()) {
			Rectangle bb = e.getBoundingBox();
			if (Dead.getInstance() == e.getState()) {
				shapeRenderer.setColor(ENEMY_COLOR);
				shapeRenderer.line(bb.x, bb.y, bb.x + bb.height, bb.y
						+ bb.width);
			}
		}
	}

	private void drawViewField(Enemy e) {
		EnemyVision vision = e.getVision();
		Vector2 viewDirection = vision.getViewDir().cpy().nor()
				.mul(vision.getMaxVisionDistance());
		float angle = (float) Math
				.toDegrees(Math.acos(vision.getViewAngleCos()));
		Vector2 left = viewDirection.cpy().rotate(-angle);
		Vector2 right = viewDirection.cpy().rotate(angle);

		Vector2 origin = e.getCenter();

		shapeRenderer.setColor(VIEWFIELD_COLOR);
		shapeRenderer.drawLineRelative(origin, right);
		shapeRenderer.drawLineRelative(origin, left);
		shapeRenderer.drawLine(left.add(origin), right.add(origin));
	}

	private void drawTarget() {
		Enemy target = Levels.getCurrent().getTarget();
		shapeRenderer.drawRect(target.getBoundingBox(), Color.LIGHT_GRAY);
	}

	private void drawPatrolPaths() {
		if (!drawingPatrolPaths)
			return;
		for (Enemy e : Levels.getCurrent().getEnemies()) {
			if (Patrol.getInstance() == e.getState()) {

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
		Vehicle avatar = Levels.getCurrent().getAvatar();
		Vector2 center = avatar.getCenter();

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
		Avatar avatar = Levels.getCurrent().getAvatar();
		Vector2 acceleration = avatar.getAcceleration();
		debugText.append(SimpleFormat.format(acceleration.x) + ", "
				+ SimpleFormat.format(acceleration.y) + "\n");
		Vector2 velocity = avatar.getVelocity();
		debugText.append(SimpleFormat.format(velocity.x) + ", "
				+ SimpleFormat.format(velocity.y) + "\n");
		debugText.append("shots alive: "
				+ Levels.getCurrent().getProjectiles().size() + "\n");
		debugText.append("fps: " + Gdx.graphics.getFramesPerSecond() + "\n");

		return debugText;
	}

	private void drawProjectiles() {
		for (Projectile p : Levels.getCurrent().getProjectiles()) {
			shapeRenderer.drawRect(p.getBoundingBox(), PROJECTILE_COLOR);
		}
	}

	private void drawAvatarText(Camera cam) {
		if (!drawingAvatarText)
			return;
		Vector2 avatarPosition = Levels.getCurrent().getAvatar().getPosition();
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
