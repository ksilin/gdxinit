package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;

import com.badlogic.gdx.math.Vector2;

public class World {

	private Avatar bob;

	private Level level;

	public World() {
		// TODO - this should be solved through inheritance or interface
		// implementation
		createDemoWorld();
	}

	private void createDemoWorld() {
		bob = new Avatar(new Vector2(7, 2));
		level = new Level();
	}

	public Avatar getBob() {
		return bob;
	}

	public Level getLevel() {
		return level;
	}
}
