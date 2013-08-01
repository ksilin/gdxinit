package info.silin.gdxinit.renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class GridRenderer {

	ShapeRenderer shapeRenderer = new ShapeRenderer();
	TextRenderer textRenderer = new TextRenderer();

	public void draw(Camera cam) {
		drawGrid(cam);
		drawGridNumbers(cam);
	}

	public void drawGrid(Camera cam) {
		float x = cam.viewportWidth;
		float y = cam.viewportHeight;
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.identity();
		shapeRenderer.setColor(0.1f, 0.5f, 0.1f, 0.5f);
		shapeRenderer.begin(ShapeType.Line);
		for (int i = 0; i <= x; i++) {
			shapeRenderer.line(i, 0, i, y);
		}
		for (int i = 0; i <= y; i++) {
			shapeRenderer.line(0, i, x, i);
		}
		shapeRenderer.end();
	}

	public void drawGridNumbers(Camera cam) {
		float x = cam.viewportWidth;
		float y = cam.viewportHeight;
		for (int i = 0; i < x; i++) {
			Vector3 vec = new Vector3(0, i, 1);
			cam.project(vec);
			if (vec.y > 0 || vec.y < y) {
				if (vec.x < 0)
					vec.x = 0;
				if (vec.x > x)
					vec.x = x;
				textRenderer.draw("" + i, vec.x, vec.y);
			}
		}
		for (int i = 0; i < y; i++) {
			Vector3 vec = new Vector3(i, 0, 1);
			cam.project(vec);
			if (vec.x > 0 || vec.x < x) {
				if (vec.y <= 0)
					vec.y = 0 + 30;
				if (vec.y > y)
					vec.y = y + 10;
				textRenderer.draw("" + i, vec.x, vec.y);
			}
		}
	}

}
