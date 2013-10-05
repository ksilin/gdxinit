package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Path;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.enemy.KillableByAvatarTouch;
import info.silin.gdxinit.entity.state.enemy.ShootAvatarOnSight;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Disposable;

public class SteeringLabLevel extends Level implements Disposable {

	BoidInputEventHandler steeringInput = new BoidInputEventHandler();

	private Boid boid;

	@Override
	public void init() {

		InputMultiplexer inputMultiplexer = Screens.getInputMultiplexer();
		Gdx.app.log("SteeringLabLevel", "adding inputHandler to multiplexer");
		inputMultiplexer.addProcessor(steeringInput);

		projectiles = new ArrayList<Projectile>();
		explosions = new ArrayList<Explosion>();
		shotRays = new ArrayList<Ray>();
		enemies = new ArrayList<Enemy>();
		Enemy enemy = new Enemy(new Vector2(4, 10));
		enemy.setPatrolPath(createPath());
		enemy.removeGlobalState(ShootAvatarOnSight.getInstance());
		enemy.removeGlobalState(KillableByAvatarTouch.getInstance());
		enemies.add(enemy);

		target = new Enemy(new Vector2(999, 999));
		avatar = new Avatar(new Vector2(-2, -10));

		boid = new Boid(new Vector2(10, 6));

		width = 20;
		height = 12;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		addBorders();

		calcNonNullBlocks();
	}

	private Path createPath() {
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

	public Boid getBoid() {
		return boid;
	}

	@Override
	public void update(float delta) {
		boid.update(delta);
		super.update(delta);
	}

	@Override
	public void dispose() {
		Screens.getInputMultiplexer().removeProcessor(steeringInput);
	}

}
