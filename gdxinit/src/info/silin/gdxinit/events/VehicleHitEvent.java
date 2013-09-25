package info.silin.gdxinit.events;

import info.silin.gdxinit.entity.Vehicle;

public class VehicleHitEvent {

	private Vehicle vehicle;

	public VehicleHitEvent(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}
}
