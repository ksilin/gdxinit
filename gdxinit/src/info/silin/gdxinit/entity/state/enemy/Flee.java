package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

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
				enemy.returnToPreviousState();
				return;
			}
		}
		if (!enemy.forgotAvatar()) {
			runFromAvatar(enemy, delta);
		}
		super.execute(enemy, delta);
	}

	private void runFromAvatar(Enemy enemy, float delta) {

		Vector2 force = Steering.flee(enemy.getLastAvatarPosition(), enemy);
		enemy.setForce(force);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Flee getInstance() {
		return INSTANCE;
	}
}
