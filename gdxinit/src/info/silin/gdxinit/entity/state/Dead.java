package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.entity.Entity;

public class Dead extends State<Entity> {

	public static Dead INSTANCE = new Dead();

	private Dead() {
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

	public static Dead getINSTANCE() {
		return INSTANCE;
	}
}
