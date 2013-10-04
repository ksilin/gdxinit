package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public class SeekSteering extends SteeringBehaviour {

	@Override
	public Vector2 getForce(Vehicle vehicle, Vector2 target) {
		return Steering.seek(target, vehicle);
	}
}
