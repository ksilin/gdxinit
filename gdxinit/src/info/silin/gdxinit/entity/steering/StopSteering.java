package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class StopSteering extends SteeringBehaviour {

	@Override
	public Vector2 getForce(Vehicle vehicle, Vector2 target,
			Vector2 targetVelocity) {
		return Vector2.Zero.cpy();
	}
}
