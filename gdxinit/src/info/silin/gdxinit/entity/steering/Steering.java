package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class Steering {

	private static final int CORRECTION = 2;

	public static Vector2 seek(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		Vector2 velocity = v.getVelocity();
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		desiredVelocity.sub(velocity).mul(-CORRECTION);
		return desiredVelocity;
	}

	public static Vector2 flee(Vector2 targetPos, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 desiredVelocity = center.cpy().sub(targetPos);
		Vector2 velocity = v.getVelocity();
		desiredVelocity.nor();
		desiredVelocity.mul(v.getMaxVelocity());
		desiredVelocity.sub(velocity).mul(CORRECTION);
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
			desiredVelocity.sub(velocity).mul(-CORRECTION);
		}
		return desiredVelocity;
	}

	public static Vector2 pursue(Vehicle target, Vehicle v) {
		Vector2 center = v.getCenter();
		Vector2 targetCenter = target.getCenter();

		Vector2 targetVelocity = target.getVelocity();
		Vector2 desiredVelocity = center.cpy().sub(targetCenter);

		Vector2 velocity = v.getVelocity();

		float ownVelocityAlignment = desiredVelocity.dot(velocity);
		float relativeHeading = targetVelocity.dot(velocity);
		if (ownVelocityAlignment > 0 && relativeHeading < -0.95)
			return seek(targetCenter, v);

		float lookAheadTime = desiredVelocity.len()
				/ (v.getMaxVelocity() + targetVelocity.len());

		Vector2 predicetTargetPos = targetCenter.cpy().add(targetVelocity)
				.cpy().mul(lookAheadTime);

		return seek(predicetTargetPos, v);
	}
}
