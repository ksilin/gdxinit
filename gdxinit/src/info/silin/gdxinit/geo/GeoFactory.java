package info.silin.gdxinit.geo;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

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
}
