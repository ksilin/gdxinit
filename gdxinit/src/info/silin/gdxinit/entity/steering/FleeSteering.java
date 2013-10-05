package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class FleeSteering extends SteeringBehaviour {

	@Override
	public Vector2 getForce(Vehicle vehicle, Vector2 target) {
		return Steering.flee(target, vehicle);
	}

	@Override
	public Vector2 getForce(Vehicle vehicle, Vehicle target) {
		return Steering.flee(target.getCenter(), vehicle);
	}
}
