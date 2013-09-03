package info.silin.gdxinit.entity.state.projectile;

import java.util.List;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;

public class Flying extends State<Projectile> {

	public static Flying INSTANCE = new Flying();

	private Flying() {
	};

	@Override
	public void enter(Projectile entity) {
		super.enter(entity);
	}

	@Override
	public void execute(Projectile entity, float delta) {

		processBlockCollisions(entity, delta);

		entity.move(delta);
		super.execute(entity, delta);
	}

	private List<Collision> processBlockCollisions(Projectile entity,
			float delta) {
		List<Collision> collisions = Collider.predictCollisions(World.INSTANCE
				.getLevel().getNonNullBlocks(), entity, delta);
		if (!collisions.isEmpty() && Flying.getINSTANCE() == entity.getState()) {
			entity.setState(Exploding.getINSTANCE());
		}
		return collisions;
	}

	@Override
	public void exit(Projectile entity) {
		super.exit(entity);
	}

	public static Flying getINSTANCE() {
		return INSTANCE;
	}
}
