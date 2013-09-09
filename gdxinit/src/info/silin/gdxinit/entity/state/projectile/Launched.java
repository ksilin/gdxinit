package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.State;

public class Launched extends State<Projectile> {

	public static Launched INSTANCE = new Launched();
	private float startCollisionCheckingAfter = 0.3f;

	private Launched() {
	};

	@Override
	public void enter(Projectile entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Projectile entity, float delta) {

		entity.move(delta);
		super.execute(entity, delta);

		if (getStateTime() > startCollisionCheckingAfter) {
			entity.setState(Flying.getInstance());
		}
	}

	@Override
	public void exit(Projectile entity) {
		super.exit(entity);
	}

	public static Launched getInstance() {
		return INSTANCE;
	}
}
