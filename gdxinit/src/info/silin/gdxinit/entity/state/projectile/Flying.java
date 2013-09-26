package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.Levels;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.Idle;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.events.AvatarHitEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

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
		// processEnemyCollisions(entity, delta);
		collideWithAvatar(entity, delta);

		entity.move(delta);
		super.execute(entity, delta);
	}

	private List<Collision> collideWithBlocks(Projectile projectile, float delta) {
		List<Collision> collisions = Collider.predictCollisions(Levels
				.getCurrent().getNonNullBlocks(), projectile, delta);
		if (!collisions.isEmpty()
				&& Flying.getInstance() == projectile.getState()) {
			projectile.setState(Exploding.getInstance());
		}
		return collisions;
	}

	private void collideWithEnemies(Projectile p, float delta) {
		ArrayList<Entity> enemyEntities = new ArrayList<Entity>(Levels
				.getCurrent().getEnemies());
		List<Collision> enemyCollisions = Collider.predictCollisions(
				enemyEntities, p, delta);
		if (!enemyCollisions.isEmpty() && Flying.getInstance() == p.getState()) {
			Gdx.app.log("Flying", "hit an enemy");
			Enemy enemy = (Enemy) enemyCollisions.get(0).getEntity1();
			enemy.setState(Dead.getInstance());
			p.setState(Idle.getInstance()); // no explosions for enemies
		}
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
