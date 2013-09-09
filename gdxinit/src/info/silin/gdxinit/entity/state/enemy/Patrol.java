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

		// int currentPathIndex = enemy.getCurrentPathIndex();
		Path patrolPath = enemy.getPatrolPath();
		Vector2 waypoint = patrolPath.getWaypoints().get(
				enemy.getCurrentPathIndex());
		Vector2 position = enemy.getPosition();
		Vector2 targetDir = waypoint.cpy().sub(position);

		// have we reached the current waypoint?
		if (targetDir.len2() < 0.2f) {

			enemy.setCurrentPathIndex(enemy.getCurrentPathIndex() + 1);

			// the patrol path is iterated in a circle, so for a
			// back-and-forth movement, all waypoints have to be repeated in
			// the path
			if (enemy.getCurrentPathIndex() == patrolPath.getWaypoints().size()) {
				enemy.setCurrentPathIndex(0);
			}
			waypoint = patrolPath.getWaypoints().get(
					enemy.getCurrentPathIndex());
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

		// update view dir - sweep
		// TODO - a hack for now
		Vector2 viewDir = enemy.getVelocity().cpy().nor();
		viewDir.rotate((float) (Math.cos(getStateTime() * 0.5f) * 20f));
		enemy.setViewDir(viewDir);
		super.execute(enemy, delta);
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
