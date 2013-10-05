package info.silin.gdxinit.entity.state.boid;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

import com.badlogic.gdx.math.Vector2;

public class Evading extends State<Boid> {

	private Vehicle target;

	public Evading(Vehicle target) {
		this.target = target;
	};

	@Override
	public void enter(Boid entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Boid enemy, float delta) {
		evadeTarget(enemy, delta);
		super.execute(enemy, delta);
	}

	private void evadeTarget(Boid v, float delta) {

		Vector2 targetCenter = target.getCenter();
		Vector2 targetVelocity = target.getVelocity();
		Vector2 desiredVelocity = v.getCenter().cpy().sub(targetCenter);

		float lookAheadTime = desiredVelocity.len()
				/ (v.getMaxVelocity() + targetVelocity.len());
		Vector2 predictedTargetPos = targetCenter.cpy().add(
				targetVelocity.cpy().mul(lookAheadTime));
		v.setTargetPos(predictedTargetPos);
		Vector2 targetForce = Steering.flee(predictedTargetPos, v);
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
