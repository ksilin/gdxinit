package info.silin.gdxinit;

import info.silin.gdxinit.entity.Bob;

import com.badlogic.gdx.math.Vector2;

public class World {

	private Bob bob;

	private Level level;

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		bob = new Bob(new Vector2(7, 2));
		level = new Level();
	}

	public Bob getBob() {
		return bob;
	}

	public Level getLevel() {
		return level;
	}
}
