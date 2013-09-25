package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class Steering {

	public static Vector2 seek(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = targetPos.cpy().sub(center);
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		Vector2 velocity = v.getVelocity();
		return (desiredVelocity.sub(velocity).mul(5));
	}

	public static Vector2 flee(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		Vector2 velocity = v.getVelocity();
		return (desiredVelocity.sub(velocity).mul(5));
	}

}
