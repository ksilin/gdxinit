package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class Steering {

	public static Vector2 seek(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		Vector2 velocity = v.getVelocity();
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		desiredVelocity.sub(velocity).mul(-5);
		return desiredVelocity;
	}

	public static Vector2 flee(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		Vector2 velocity = v.getVelocity();
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		desiredVelocity.sub(velocity).mul(5);
		return desiredVelocity;
	}

	public static Vector2 arrive(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		Vector2 velocity = v.getVelocity();

		float len = desiredVelocity.len();
		if (len > 0.05f) {
			desiredVelocity.nor();
			float min = Math.min(len, v.getMaxVelocity());
			desiredVelocity.mul(min);
			desiredVelocity.sub(velocity).mul(-5);
		}
		return desiredVelocity;
	}
}
