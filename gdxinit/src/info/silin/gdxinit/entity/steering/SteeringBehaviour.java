package info.silin.gdxinit.entity.steering;

import info.silin.gdxinit.entity.Vehicle;

import com.badlogic.gdx.math.Vector2;

public abstract class SteeringBehaviour {

	public abstract Vector2 getForce(Vehicle vehicle, Vector2 tagret);

	public abstract Vector2 getForce(Vehicle vehicle, Vehicle target);
}
