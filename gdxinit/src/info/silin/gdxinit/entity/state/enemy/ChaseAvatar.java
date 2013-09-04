package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;

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
				enemy.setState(Patrol.getINSTANCE());
				return;
			}
		}
		if (!enemy.forgotAvatar()) {
			goToAvatar(enemy, delta);
		}
		super.execute(enemy, delta);
	}

	private void goToAvatar(Enemy enemy, float delta) {

		Vector2 direction = enemy.getLastAvatarPosition().cpy()
				.sub(enemy.getBoundingBoxCenter());

		enemy.setForce(direction.mul(3f));
		enemy.move(delta);
		Collider.pushBack(enemy, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static ChaseAvatar getINSTANCE() {
		return INSTANCE;
	}
}
