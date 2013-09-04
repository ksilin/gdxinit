package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;

import com.badlogic.gdx.math.Vector2;

public class Flee extends State<Enemy> {

	public static Flee INSTANCE = new Flee();

	private Flee() {
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
			runFromAvatar(enemy, delta);
		}
		// enemy.shoot(World.INSTANCE.getAvatar().getBoundingBoxCenter());
		super.execute(enemy, delta);
	}

	private void runFromAvatar(Enemy enemy, float delta) {

		Vector2 direction = enemy.getLastAvatarPosition().cpy()
				.sub(enemy.getBoundingBoxCenter()).mul(-1f);

		enemy.setForce(direction.mul(3f));
		enemy.move(delta);
		Collider.pushBack(enemy, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Flee getINSTANCE() {
		return INSTANCE;
	}
}
