package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Path;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.enemy.FleeFromAvatarOnSight;
import info.silin.gdxinit.entity.state.enemy.LookAround;
import info.silin.gdxinit.entity.state.enemy.ShootAvatarOnSight;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public class DemoLevel extends Level {

	public DemoLevel() {
		init();
	}

	@Override
	public void init() {

		projectiles = new ArrayList<Projectile>();
		explosions = new ArrayList<Explosion>();
		shotRays = new ArrayList<Ray>();

		width = 20;
		height = 12;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		fillFromTo(1, 8, 5, 8);
		blocks[5][9] = new Block(new Vector2(5, 9));

		fillFromTo(3, 3, 5, 5);
		blocks[4][4] = null;
		blocks[5][4] = null;

		fillFromTo(8, 5, 8, 8);
		fillFromTo(8, 1, 8, 2);

		fillFromTo(11, 3, 11, 5);

		fillFromTo(11, 8, 13, 9);
		blocks[12][9] = null;

		fillFromTo(14, 1, 14, 2);

		fillFromTo(14, 5, 18, 5);
		blocks[16][8] = new Block(new Vector2(16, 8));

		addBorders();

		avatar = new Avatar(new Vector2(2, 10));

		createEnemies();

		addDemoAssasinationTarget();
	}

	private void addDemoAssasinationTarget() {
		target = new Enemy(new Vector2(16.5f, 3.5f));
		target.removeGlobalState(ShootAvatarOnSight.getInstance());
		target.addGlobalState(FleeFromAvatarOnSight.getInstance());
		target.setState(LookAround.getInstance());
		target.setWeapon(null);
		target.getVision().setTargetViewDir(new Vector2(0f, 1f));
	}

	private void createEnemies() {
		enemies = new ArrayList<Enemy>();
		Enemy enemy1 = new Enemy(new Vector2(2, 4.5f));
		enemy1.setPatrolPath(createPath1());
		enemies.add(enemy1);

		Enemy enemy2 = new Enemy(new Vector2(7, 7));
		enemy2.setPatrolPath(createPath2());
		enemies.add(enemy2);

		Enemy enemy3 = new Enemy(new Vector2(15, 8.5f));
		enemy3.setPatrolPath(createPath3());
		enemies.add(enemy3);

		Enemy enemy4 = new Enemy(new Vector2(10, 6f));
		enemy4.setPatrolPath(createPath3());
		enemies.add(enemy4);
	}

	private Path createPath1() {
		Path path = new Path();

		Vector2 waypoint1 = new Vector2(2, 7);
		Vector2 waypoint2 = new Vector2(7, 7);
		Vector2 waypoint3 = new Vector2(7, 2);
		Vector2 waypoint4 = new Vector2(2, 2);

		List<Vector2> waypoints = path.getWaypoints();
		waypoints.add(waypoint1);
		waypoints.add(waypoint2);
		waypoints.add(waypoint3);
		waypoints.add(waypoint4);
		return path;
	}

	private Path createPath2() {
		Path path = new Path();

		Vector2 waypoint1 = new Vector2(7, 10);
		Vector2 waypoint2 = new Vector2(10, 10);
		Vector2 waypoint3 = new Vector2(10, 4);
		Vector2 waypoint4 = new Vector2(7, 4);

		List<Vector2> waypoints = path.getWaypoints();
		waypoints.add(waypoint1);
		waypoints.add(waypoint2);
		waypoints.add(waypoint3);
		waypoints.add(waypoint4);
		return path;
	}

	private Path createPath3() {
		Path path = new Path();

		Vector2 waypoint1 = new Vector2(15, 10);
		Vector2 waypoint2 = new Vector2(18, 10);
		Vector2 waypoint3 = new Vector2(18, 7);
		Vector2 waypoint4 = new Vector2(15, 7);
		Vector2 waypoint5 = new Vector2(10, 7);
		Vector2 waypoint6 = new Vector2(10, 2);
		Vector2 waypoint7 = new Vector2(13, 2);
		Vector2 waypoint8 = new Vector2(13, 7);
		Vector2 waypoint9 = new Vector2(15, 7);

		List<Vector2> waypoints = path.getWaypoints();
		waypoints.add(waypoint1);
		waypoints.add(waypoint2);
		waypoints.add(waypoint3);
		waypoints.add(waypoint4);
		waypoints.add(waypoint5);
		waypoints.add(waypoint6);
		waypoints.add(waypoint7);
		waypoints.add(waypoint8);
		waypoints.add(waypoint9);
		return path;
	}
}