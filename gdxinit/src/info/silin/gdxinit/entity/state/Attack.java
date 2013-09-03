package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.entity.Enemy;

public class Attack extends State<Enemy> {

	public static Attack INSTANCE = new Attack();

	private Attack() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy entity, float delta) {
		super.execute(entity, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Attack getINSTANCE() {
		return INSTANCE;
	}
}
