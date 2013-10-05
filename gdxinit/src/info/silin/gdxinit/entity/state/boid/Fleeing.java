package info.silin.gdxinit.entity.state.boid;

import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

import com.badlogic.gdx.math.Vector2;

public class Fleeing extends State<Boid> {

	private Vector2 target;

	public Fleeing(Vector2 target) {
		this.target = target;
	};

	@Override
	public void enter(Boid entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Boid enemy, float delta) {
		fleeFromTarget(enemy, delta);
		super.execute(enemy, delta);
	}

	private void fleeFromTarget(Boid v, float delta) {
		Vector2 targetForce = Steering.flee(target, v);
		v.setForce(targetForce);
	}

	@Override
	public void exit(Boid entity) {
		super.exit(entity);
	}

	public Vector2 getTarget() {
		return target;
	}
}
