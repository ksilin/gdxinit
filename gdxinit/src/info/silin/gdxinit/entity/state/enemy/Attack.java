package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;

import com.badlogic.gdx.math.Vector2;

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
			enemy.notSeingAvatar(delta);
			if (enemy.forgotAvatar()) {
				enemy.setState(Patrol.getINSTANCE());
				return;
			}
		} else {
			enemy.seingAvatar();
		}
		if (!enemy.forgotAvatar()) {
			goToAvatar(enemy, delta);
		}
		enemy.shoot(World.INSTANCE.getAvatar().getBoundingBoxCenter());
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

	public static Attack getINSTANCE() {
		return INSTANCE;
	}
}
