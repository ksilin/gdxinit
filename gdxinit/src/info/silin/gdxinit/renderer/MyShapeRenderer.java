package info.silin.gdxinit.renderer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MyShapeRenderer extends ShapeRenderer {

	public void drawRect(Rectangle rect, Color color) {
		setColor(color);
		rect(rect.x, rect.y, rect.width, rect.height);
	}

	public void drawRect(Rectangle rect) {
		rect(rect.x, rect.y, rect.width, rect.height);
	}

	public void drawLine(Vector2 start, Vector2 end, Color color) {
		setColor(color);
		line(start.x, start.y, end.x, end.y);
	}

	public void drawLine(Vector2 start, Vector2 end) {
		line(start.x, start.y, end.x, end.y);
	}

	public void drawLineRelative(Vector2 start, Vector2 v, Color color) {
		setColor(color);
		line(start.x, start.y, start.x + v.x, start.y + v.y);
	}

	public void drawLineRelative(Vector2 start, Vector2 v) {
		line(start.x, start.y, start.x + v.x, start.y + v.y);
	}

	public void drawFilledRect(Rectangle rect, Color color) {
		setColor(color);
		filledRect(rect.x, rect.y, rect.width, rect.height);
	}

	public void drawFilledRect(Rectangle rect) {
		filledRect(rect.x, rect.y, rect.width, rect.height);
	}
}
