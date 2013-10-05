package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

import com.badlogic.gdx.math.Vector2;

public class ChaseAvatar extends State<Enemy> {

	public static ChaseAvatar INSTANCE = new ChaseAvatar();

	private ChaseAvatar() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy enemy, float delta) {

		if (!enemy.canSeeAvatar()) {
			if (enemy.forgotAvatar()) {
				enemy.setState(Patrol.getInstance());
				return;
			}
		}
		if (!enemy.forgotAvatar()) {
			goToAvatar(enemy, delta);
			lookWhereYouGo(enemy);
		}
		super.execute(enemy, delta);
	}

	private void lookWhereYouGo(Enemy enemy) {
		enemy.getVision().setTargetViewDir(enemy.getVelocity());
	}

	private void goToAvatar(Enemy enemy, float delta) {

		Vector2 targetForce = Steering.seek(enemy.getLastAvatarPosition(),
				enemy);
		enemy.setForce(targetForce);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static ChaseAvatar getInstance() {
		return INSTANCE;
	}
}
