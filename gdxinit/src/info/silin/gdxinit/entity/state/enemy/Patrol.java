package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Path;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;

import com.badlogic.gdx.math.Vector2;

public class Patrol extends State<Enemy> {

	public static Patrol INSTANCE = new Patrol();

	private Patrol() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy enemy, float delta) {

		Path patrolPath = enemy.getPatrolPath();
		Vector2 waypoint = patrolPath.getCurrentWaypoint();
		Vector2 position = enemy.getPosition();
		Vector2 targetDir = waypoint.cpy().sub(position);

		// have we reached the current waypoint?
		if (targetDir.len2() < 0.5f) {
			patrolPath.next();
			waypoint = patrolPath.getCurrentWaypoint();
			targetDir = waypoint.cpy().sub(position);
		}

		Vector2 targetForce = targetDir.nor().mul(enemy.getMaxForce());
		enemy.setForce(targetForce);

		if (enemy.canSeeAvatar()) {
			if (shouldAttackAvatar(enemy)) {
				enemy.setState(ChaseAvatar.getInstance());
			} else {
				enemy.setState(Flee.getInstance());
			}
		}
		enemy.move(delta);
		Collider.pushBack(enemy, delta);

		lookAround(enemy);

		super.execute(enemy, delta);
	}

	private void lookAround(Enemy enemy) {
		Vector2 viewDir = enemy.getVelocity().cpy().nor();
		viewDir.rotate((float) (Math.cos(getStateTime() * 0.5f) * 20f));
		enemy.setViewDir(viewDir);
	}

	private boolean shouldAttackAvatar(Enemy enemy) {
		return null != enemy.getWeapon();
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Patrol getInstance() {
		return INSTANCE;
	}
}
