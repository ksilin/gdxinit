package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.geo.Collider;

public class KillableByAvatarTouch extends State<Vehicle> {

	public static KillableByAvatarTouch INSTANCE = new KillableByAvatarTouch();

	private KillableByAvatarTouch() {
	};

	@Override
	public void enter(Vehicle vehicle) {
		super.enter(vehicle);
	}

	@Override
	public void execute(Vehicle vehicle, float delta) {
		if (null != Collider.getCollision(World.INSTANCE.getAvatar(), vehicle,
				delta)) {
			vehicle.setState(Dead.getINSTANCE());

		}
		super.execute(vehicle, delta);
	}

	@Override
	public void exit(Vehicle vehicle) {
		super.exit(vehicle);
	}

	public static KillableByAvatarTouch getINSTANCE() {
		return INSTANCE;
	}
}
