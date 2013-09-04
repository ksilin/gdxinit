package info.silin.gdxinit.geo;

import com.badlogic.gdx.math.Vector2;

public final class GeoUtils {

	public static void constrain(Vector2 vector, float max) {
		if (vector.len2() > max * max) {
			vector.nor().mul(max);
		}
	}

}
