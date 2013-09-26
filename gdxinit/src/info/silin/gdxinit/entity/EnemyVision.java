package info.silin.gdxinit.entity;

import info.silin.gdxinit.World;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.GeoFactory;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class EnemyVision {

	private float maxVisionDistance = 10f;
	private double viewAngleCos = 0.75; // 30deg to each side = 60deg

	private Vector2 viewDir = new Vector2(0f, 1f);
	private Vector2 targetViewDir = new Vector2(0f, 1f);
	private float maxViewRotationSpeed = 180; // degrees per second

	private Enemy owner;

	public EnemyVision(Enemy owner) {
		this.owner = owner;
	}

	public void update(float delta) {
		lookThisWay(targetViewDir, delta);
		Avatar avatar = World.INSTANCE.getAvatar();

		if (canSeeAvatar(avatar)) {
			Gdx.app.log("Enemy", "seeing avatar ");
			owner.setLastAvatarPosition(World.INSTANCE.getAvatar()
					.getPosition().cpy());
			owner.setTimeSinceSeenAvatar(0);
			return;
		}
		owner.setTimeSinceSeenAvatar(owner.getTimeSinceSeenAvatar() + delta);
	}

	private boolean canSeeAvatar(Avatar avatar) {
		return isAvatarCloseEnough(avatar) && isAvatarInsideViewAngle(avatar)
				&& !isAvatarBehindObstacle(avatar);
	}

	private boolean isAvatarBehindObstacle(Avatar avatar) {
		Polygon viewRay = GeoFactory.fromSegment(owner.getCenter(),
				avatar.getCenter());
		List<Entity> nonNullBlocks = World.INSTANCE.getLevel()
				.getNonNullBlocks();

		List<Entity> collidingEntities = Collider.getCollidingEntities(
				nonNullBlocks, viewRay);
		boolean behindObstacle = !collidingEntities.isEmpty();
		return behindObstacle;
	}

	private boolean isAvatarInsideViewAngle(Avatar avatar) {
		Vector2 dir = avatar.getCenter().sub(owner.getCenter()).nor();
		float cos = dir.dot(viewDir.cpy().nor());
		return cos > viewAngleCos;
	}

	public boolean isAvatarCloseEnough(Avatar avatar) {
		Vector2 dir = avatar.getCenter().sub(owner.getCenter());
		return dir.len2() < maxVisionDistance * maxVisionDistance;
	}

	public void lookAt(Vector2 target, float delta) {
		lookThisWay(target.cpy().sub(owner.getCenter()), delta);
	}

	public void lookThisWay(Vector2 direction, float delta) {

		Vector2 normalizedTargetDir = direction.cpy().nor();

		double currentAngle = Math.atan2(viewDir.x, viewDir.y);
		double angle = Math.atan2(normalizedTargetDir.x, normalizedTargetDir.y);
		double diff = angle - currentAngle;
		if (Math.abs(diff) > 0.01) {
			double degrees = Math.toDegrees(diff);
			double absDegrees = Math.abs(degrees);
			double signum = Math.signum(degrees);
			double truncated = Math.min(absDegrees, maxViewRotationSpeed
					* delta);
			float signified = (float) (truncated * (-signum));
			viewDir.rotate(signified);
		}
	}

	public float getMaxVisionDistance() {
		return maxVisionDistance;
	}

	public void setMaxVisionDistance(float maxVisionDistance) {
		this.maxVisionDistance = maxVisionDistance;
	}

	public double getViewAngleCos() {
		return viewAngleCos;
	}

	public void setViewAngleCos(double viewAngleCos) {
		this.viewAngleCos = viewAngleCos;
	}

	public Vector2 getViewDir() {
		return viewDir;
	}

	public Vector2 getTargetViewDir() {
		return targetViewDir;
	}

	public void setTargetViewDir(Vector2 targetViewDir) {
		this.targetViewDir = targetViewDir.cpy().nor();
	}

}
