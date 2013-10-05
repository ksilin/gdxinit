package info.silin.gdxinit.entity.state.boid;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Pursuing extends State<Boid> {

	private Vehicle target;

	public Pursuing(Vehicle target) {
		this.target = target;
	};

	@Override
	public void enter(Boid entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Boid enemy, float delta) {
		goToTarget(enemy, delta);
		super.execute(enemy, delta);
	}

	private void goToTarget(Boid v, float delta) {

		Vector2 center = v.getCenter();
		Vector2 targetCenter = target.getCenter();
		Vector2 targetVelocity = target.getVelocity();
		Vector2 desiredVelocity = center.cpy().sub(targetCenter);

		Vector2 velocity = v.getVelocity();
		float ownVelocityAlignment = desiredVelocity.dot(velocity);
		float relativeHeading = targetVelocity.dot(velocity);
		if (ownVelocityAlignment > 0 && relativeHeading < -0.95) {
			v.setTargetPos(targetCenter);
			Vector2 targetForce = Steering.seek(targetCenter, v);
			v.setForce(targetForce);
			return;
		}

		float lookAheadTime = desiredVelocity.len()
				/ (v.getMaxVelocity() + targetVelocity.len());
		Gdx.app.log("Pursuing", "look ahead time: " + lookAheadTime);

		Vector2 predictedTargetPos = targetCenter.cpy().add(
				targetVelocity.cpy().mul(lookAheadTime));
		v.setTargetPos(predictedTargetPos);
		Vector2 targetForce = Steering.seek(predictedTargetPos, v);
		v.setForce(targetForce);
	}

	@Override
	public void exit(Boid entity) {
		super.exit(entity);
	}

	public Vehicle getTarget() {
		return target;
	}
}
