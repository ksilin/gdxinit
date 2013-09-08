package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;

public class ShootAvatarOnSight extends State<Enemy> {

	public static ShootAvatarOnSight INSTANCE = new ShootAvatarOnSight();

	private ShootAvatarOnSight() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy enemy, float delta) {

		if (!enemy.canSeeAvatar()) {
			return;
		}
		enemy.shoot(World.INSTANCE.getAvatar().getCenter());
		super.execute(enemy, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static ShootAvatarOnSight getINSTANCE() {
		return INSTANCE;
	}
}
