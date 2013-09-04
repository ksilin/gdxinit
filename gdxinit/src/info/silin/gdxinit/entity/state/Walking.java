package info.silin.gdxinit.entity.state;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Vehicle;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;

import java.util.List;

import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

public class Walking extends State<Vehicle> {

	public static Walking INSTANCE = new Walking();

	private Walking() {
	};

	@Override
	public void enter(Vehicle entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Vehicle entity, float delta) {
		entity.move(delta);
		pushBack(entity, delta);
		super.execute(entity, delta);
	}

	@Override
	public void exit(Vehicle entity) {
		super.exit(entity);
	}

	public static Walking getINSTANCE() {
		return INSTANCE;
	}

	private void pushBack(Vehicle entity, float delta) {
		List<Collision> collisions = Collider.predictCollisions(World.INSTANCE
				.getLevel().getNonNullBlocks(), entity, delta);
		for (Collision c : collisions) {
			MinimumTranslationVector translation = c.getTranslation();
			entity.getPosition().add(translation.normal.x * translation.depth,
					translation.normal.y * translation.depth);
		}
	}
}
