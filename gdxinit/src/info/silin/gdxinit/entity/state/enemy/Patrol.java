package info.silin.gdxinit.entity.state.enemy;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Path;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.steering.Steering;

import com.badlogic.gdx.math.Vector2;

public class Patrol extends State<Enemy> {

	// TODO - as 4 enemies have the same state, they all increment the global
	// stateTime...I have to give each Entity a state instance -> pooling
	private static final float SLOWDOWN = 0.25f;
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
		Vector2 targetForce = Steering.seek(waypoint, enemy);
		enemy.setForce(targetForce);

		if (enemy.canSeeAvatar()) {
			if (shouldAttackAvatar(enemy)) {
				enemy.setState(ChaseAvatar.getInstance());
			} else {
				enemy.setState(Flee.getInstance());
			}
		}
		lookWhereYouGo(enemy);
		super.execute(enemy, delta * SLOWDOWN);
	}

	private void lookWhereYouGo(Enemy enemy) {
		enemy.getVision().setTargetViewDir(enemy.getVelocity());
	}

	private void lookAround(Enemy enemy) {
		Vector2 viewDir = enemy.getVelocity().cpy().nor();
		viewDir.rotate((float) (Math.cos(getStateTime() * 0.5f) * 20f));
		enemy.getVision().setTargetViewDir(viewDir);
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
