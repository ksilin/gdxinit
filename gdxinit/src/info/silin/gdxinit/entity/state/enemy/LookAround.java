package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;

public class LookAround extends State<Enemy> {

	public static LookAround INSTANCE = new LookAround();

	private LookAround() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy enemy, float delta) {
		enemy.getViewDir().rotate(delta * 10f);
		super.execute(enemy, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static LookAround getInstance() {
		return INSTANCE;
	}
}
