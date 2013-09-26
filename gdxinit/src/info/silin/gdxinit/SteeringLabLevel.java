package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public class SteeringLabLevel extends Level {

	public SteeringLabLevel() {
		init();
	}

	@Override
	public void init() {

		projectiles = new ArrayList<Projectile>();
		explosions = new ArrayList<Explosion>();
		shotRays = new ArrayList<Ray>();
		enemies = new ArrayList<Enemy>();
		target = new Enemy(new Vector2(999, 999));

		width = 20;
		height = 12;
		blocks = new Block[width][height];

		prefillLevelWithNulls(width, height);

		addBorders();

		avatar = new Avatar(new Vector2(2, 10));
	}
}
