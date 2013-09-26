package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.Levels;
import info.silin.gdxinit.audio.Sounds;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;

public class ShootAvatarOnSight extends State<Enemy> {

	public static ShootAvatarOnSight INSTANCE = new ShootAvatarOnSight();

	private float reloadTime = 0.5f;

	private float reloadTimeLeft = reloadTime;

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
		if (enemy.getTimeSeeingAvatar() < delta + 0.0001f) {
			reloadTimeLeft = reloadTime;
			Sounds.reload.play();
		}
		if (reloadTimeLeft > 0) {
			reload(delta);
			return;
		}
		enemy.shoot(Levels.getCurrent().getAvatar().getCenter());
		super.execute(enemy, delta);
	}

	private void reload(float delta) {
		reloadTimeLeft -= delta;
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static ShootAvatarOnSight getInstance() {
		return INSTANCE;
	}
}
