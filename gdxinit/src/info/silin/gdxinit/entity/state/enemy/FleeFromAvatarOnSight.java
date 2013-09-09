package info.silin.gdxinit.entity.state.enemy;

import com.badlogic.gdx.math.Vector2;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.state.State;

public class FleeFromAvatarOnSight extends State<Enemy> {

	public static FleeFromAvatarOnSight INSTANCE = new FleeFromAvatarOnSight();

	private FleeFromAvatarOnSight() {
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
		enemy.setState(Flee.getInstance());
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static FleeFromAvatarOnSight getInstance() {
		return INSTANCE;
	}
}
