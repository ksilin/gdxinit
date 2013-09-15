package info.silin.gdxinit.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GridRenderer {

	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	private TextRenderer textRenderer = new TextRenderer();

	private Color gridColor = new Color(0.1f, 0.5f, 0.1f, 0.5f);

	public void draw(Camera cam) {
		drawGrid(cam);
		drawGridNumbers(cam);
	}

	public void drawGrid(Camera cam) {
		float width = cam.viewportWidth;
		float height = cam.viewportHeight;

		Vector3 position = cam.position;
		float minX = (float) Math.floor(position.x - width / 2);
		float minY = (float) Math.floor(position.y - height / 2);
		float maxX = (float) Math.ceil(position.x + width / 2);
		float maxY = (float) Math.ceil(position.y + height / 2);

		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.setColor(gridColor);

		shapeRenderer.begin(ShapeType.Line);
		for (float i = minX; i <= maxX; i++) {
			shapeRenderer.line(i, 0, i, maxY);
		}
		for (float i = minY; i <= maxY; i++) {
			shapeRenderer.line(0, i, maxX, i);
		}
		shapeRenderer.end();
	}

	public void drawGridNumbers(Camera cam) {

		// TODO - should work only if cam axes and world axes are pointing in
		// the same dir. what if the cam is flipped?
		Vector2 min = getMinVector(cam.frustum);
		Vector2 max = getMaxVector(cam.frustum);

		drawXLabels(cam, min, max);
		drawYLabels(cam, min, max);
	}

	private void drawYLabels(Camera cam, Vector2 min, Vector2 max) {
		for (float i = min.y; i < max.y; i++) {
			Vector3 vec = new Vector3(0, i, 1);
			cam.project(vec);
			constrainX(vec);
			textRenderer.draw(String.valueOf(i), vec.x, vec.y);
		}
	}

	private void drawXLabels(Camera cam, Vector2 min, Vector2 max) {
		for (float i = min.x; i < max.x; i++) {
			Vector3 vec = new Vector3(i, 0, 1);
			cam.project(vec);
			constrainY(vec);
			textRenderer.draw(String.valueOf(i), vec.x, vec.y);
		}
	}

	private Vector2 getMaxVector(Frustum frustum) {
		Vector3[] frustumPoints = frustum.planePoints;
		Vector3 topRight = frustumPoints[2];
		float maxX = (float) Math.ceil(topRight.x);
		float maxY = (float) Math.ceil(topRight.y);
		return new Vector2(maxX, maxY);
	}

	private Vector2 getMinVector(Frustum frustum) {
		Vector3[] frustumPoints = frustum.planePoints;
		Vector3 bottomLeft = frustumPoints[0];
		float minX = (float) Math.floor(bottomLeft.x);
		float minY = (float) Math.floor(bottomLeft.y);
		return new Vector2(minX, minY);
	}

	private void constrainX(Vector3 vec) {
		if (vec.x < 0)
			vec.x = 0;
	}

	private void constrainY(Vector3 vec) {
		if (vec.y <= 0)
			vec.y = 0 + 15;
	}
}
