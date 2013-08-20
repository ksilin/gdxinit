package info.silin.gdxinit.geo;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GeoFactory {

	public static Polygon fromRectangle(Rectangle r) {
		float[] vertexCoords = new float[8];

		vertexCoords[0] = r.x;
		vertexCoords[1] = r.y;

		vertexCoords[2] = r.x + r.width;
		vertexCoords[3] = r.y;

		vertexCoords[4] = r.x + r.width;
		vertexCoords[5] = r.y + r.height;

		vertexCoords[6] = r.x;
		vertexCoords[7] = r.y + r.height;
		return new Polygon(vertexCoords);
	}

	/**
	 * Creates a polygon representation of a segment as a narrow rectangle.
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public static Polygon fromSegment(Vector2 start, Vector2 end) {
		float[] vertexCoords = new float[8];

		Vector2 perp = end.cpy().sub(start).rotate(90).nor().mul(0.1f);

		Vector2 corner1 = start.cpy().add(perp);
		Vector2 corner2 = start.cpy().sub(perp);

		Vector2 corner3 = end.cpy().add(perp);
		Vector2 corner4 = end.cpy().sub(perp);

		vertexCoords[0] = corner1.x;
		vertexCoords[1] = corner1.y;

		vertexCoords[2] = corner2.x;
		vertexCoords[3] = corner2.y;

		vertexCoords[4] = corner3.x;
		vertexCoords[5] = corner3.y;

		vertexCoords[6] = corner4.x;
		vertexCoords[7] = corner4.y;
		return new Polygon(vertexCoords);
	}
}
