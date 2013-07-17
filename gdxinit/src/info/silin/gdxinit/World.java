package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.geo.Collision;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class World {

	private Avatar avatar;
	private Level level;

	// debug info
	private List<Collision> collisions;

	public World() {
		// TODO - this should be solved through inheritance or interface
		// implementation
		createDemoWorld();
	}

	private void createDemoWorld() {
		avatar = new Avatar(new Vector2(2, 3));
		level = new Level();
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public Level getLevel() {
		return level;
	}

	public List<Collision> getCollisions() {
		return collisions;
	}

	public void setCollisions(List<Collision> collisions) {
		this.collisions = collisions;
	}
}
