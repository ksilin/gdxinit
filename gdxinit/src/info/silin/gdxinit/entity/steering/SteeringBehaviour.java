package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public abstract class SteeringBehaviour {

	public abstract Vector2 getForce(Vehicle vehicle, Vector2 tagret,
			Vector2 targetVelocity);
}
