package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.entity.Vehicle;

public class Walking extends State<Vehicle> {

	public static Walking INSTANCE = new Walking();

	private Walking() {
	};

	@Override
	public void enter(Vehicle entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Vehicle entity, float delta) {
		super.execute(entity, delta);
	}

	@Override
	public void exit(Vehicle entity) {
		super.exit(entity);
	}

	public static Walking getInstance() {
		return INSTANCE;
	}

}
