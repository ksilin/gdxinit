package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class PursueSteering extends SteeringBehaviour {

	// TODO this is wrong
	@Override
	public Vector2 getForce(Vehicle vehicle, Vector2 target) {
		return Steering.seek(target, vehicle);
	}

	@Override
	public Vector2 getForce(Vehicle vehicle, Vehicle target) {
		return Steering.pursue(target, vehicle);
	}
}
