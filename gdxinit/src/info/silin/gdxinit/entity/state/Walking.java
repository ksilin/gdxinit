package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.entity.Entity;

public class Walking extends State<Entity> {

	public static Walking INSTANCE = new Walking();

	private Walking() {
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

	public static Walking getINSTANCE() {
		return INSTANCE;
	}
}
