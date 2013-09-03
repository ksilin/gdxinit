package info.silin.gdxinit.entity.state;

import com.badlogic.gdx.math.Vector2;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Path;

public class Patrol extends State<Enemy> {

	public static Patrol INSTANCE = new Patrol();

	private Patrol() {
	};

	@Override
	public void enter(Enemy entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Enemy entity, float delta) {

		int currentPathIndex = entity.getCurrentPathIndex();
		Path patrolPath = entity.getPatrolPath();
		Vector2 waypoint = patrolPath.getWaypoints().get(currentPathIndex);
		Vector2 position = entity.getPosition();
		Vector2 targetDir = waypoint.cpy().sub(position);

		// have we reached the current waypoint?
		if (targetDir.len2() < 0.2f) {

			entity.setCurrentPathIndex(++currentPathIndex);

			// the patrol path is iterated in a circle, so for a
			// back-and-forth movement, all waypoints have to be repeated in
			// the path
			if (currentPathIndex == patrolPath.getWaypoints().size()) {
				currentPathIndex = 0;
			}
			waypoint = patrolPath.getWaypoints().get(currentPathIndex);
			targetDir = waypoint.cpy().sub(position);
		}

		Vector2 targetAcc = targetDir.nor().mul(Enemy.ACCELERATION_FACTOR);
		entity.getAcceleration().add(targetAcc);
		super.execute(entity, delta);
	}

	@Override
	public void exit(Enemy entity) {
		super.exit(entity);
	}

	public static Patrol getINSTANCE() {
		return INSTANCE;
	}
}
