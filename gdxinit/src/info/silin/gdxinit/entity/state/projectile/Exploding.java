package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.state.State;

public class Exploding extends State<Entity> {

	public static Exploding INSTANCE = new Exploding();

	private Exploding() {
	};

	@Override
	public void enter(Entity entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Entity entity, float delta) {
		super.execute(entity, delta);
	}

	@Override
	public void exit(Entity entity) {
		super.exit(entity);
	}

	public static Exploding getInstance() {
		return INSTANCE;
	}
}
