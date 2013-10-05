package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.Level;
import info.silin.gdxinit.Levels;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.events.AvatarHitEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;

import java.util.List;

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

		collideWithBlocks(entity, delta);
		collideWithAvatar(entity, delta);
		super.execute(entity, delta);
	}

	private List<Collision> collideWithBlocks(Projectile projectile, float delta) {

		int maxCoveredDistance = (int) (projectile.getVelocity().len() * delta);
		Level level = Levels.getCurrent();
		List<Collision> collisions = Collider.predictCollisions(
				level.getBlocksAround(projectile, maxCoveredDistance),
				projectile, delta);
		if (!collisions.isEmpty()
				&& Flying.getInstance() == projectile.getState()) {
			projectile.setState(Exploding.getInstance());
		}
		return collisions;
	}

	private void collideWithAvatar(Projectile p, float delta) {
		Avatar avatar = Levels.getCurrent().getAvatar();

		Collision targetCollision = Collider.getCollision(avatar, p, delta);
		if (targetCollision != null) {
			p.setState(Dead.getInstance());
			Events.post(new AvatarHitEvent());
		}
	}

	@Override
	public void exit(Projectile entity) {
		super.exit(entity);
	}

	public static Flying getInstance() {
		return INSTANCE;
	}
}
