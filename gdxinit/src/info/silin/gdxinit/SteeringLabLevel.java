package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Boid;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;

import java.util.ArrayList;

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
		target = new Enemy(new Vector2(999, 999));
		avatar = new Avatar(new Vector2(2, 10));

		boid = new Boid(new Vector2(10, 6));

		width = 20;
		height = 12;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		addBorders();

		calcNonNullBlocks();
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
