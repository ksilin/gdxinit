package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;

public class Attack extends State<Enemy> {

	public static Attack INSTANCE = new Attack();

	private Attack() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy enemy, float delta) {
		if (!enemy.canSeeAvatar()) {
			enemy.setState(Patrol.getINSTANCE());
			return;
		}
		enemy.shoot(World.INSTANCE.getAvatar().getBoundingBoxCenter());
		super.execute(enemy, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Attack getINSTANCE() {
		return INSTANCE;
	}
}
