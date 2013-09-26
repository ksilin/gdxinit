package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.Levels;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.VehicleHitEvent;
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
		if (null != Collider.getCollision(Levels.getCurrent().getAvatar(),
				vehicle, delta)) {
			Events.post(new VehicleHitEvent(vehicle));
			vehicle.setState(Dead.getInstance());

		}
		super.execute(vehicle, delta);
	}

	@Override
	public void exit(Vehicle vehicle) {
		super.exit(vehicle);
	}

	public static KillableByAvatarTouch getInstance() {
		return INSTANCE;
	}
}
