package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Block;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.Weapon;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

// Avatar has a weapon and can shoot around
public class WeaponsLabLevel extends Level {

	public WeaponsLabLevel() {
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
		blocks[16][8] = new

		Block(new Vector2(16, 8));

		addBorders();

		calcNonNullBlocks();

		avatar = new Avatar(new Vector2(2, 10));
		Weapon weapon = new Weapon();
		weapon.setCooldownTime(0.1f);
		avatar.setWeapon(weapon);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}
