package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.State;

public class Flying extends State<Projectile> {

	public static Flying INSTANCE = new Flying();

	private Flying() {
	};

	@Override
	public void enter(Projectile entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Projectile entity, float delta) {
		entity.move(delta);
		super.execute(entity, delta);
	}

	@Override
	public void exit(Projectile entity) {
		super.exit(entity);
	}

	public static Flying getINSTANCE() {
		return INSTANCE;
	}
}
