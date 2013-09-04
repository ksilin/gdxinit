package info.silin.gdxinit.entity.state.projectile;

import info.silin.gdxinit.World;
import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.Idle;
import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;
import info.silin.gdxinit.renderer.RendererController;

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

		processBlockCollisions(entity, delta);
		// processEnemyCollisions(entity, delta);
		// processTargetCollisions(entity, delta);
		processAvatarCollisions(entity, delta);

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

	private void processEnemyCollisions(Projectile p, float delta) {
		ArrayList<Entity> enemyEntities = new ArrayList<Entity>(
				World.INSTANCE.getEnemies());
		List<Collision> enemyCollisions = Collider.predictCollisions(
				enemyEntities, p, delta);
		if (!enemyCollisions.isEmpty() && Flying.getINSTANCE() == p.getState()) {
			Gdx.app.log("Flying", "hit an enemy");
			Enemy enemy = (Enemy) enemyCollisions.get(0).getEntity1();
			enemy.setState(Dead.getINSTANCE());
			p.setState(Idle.getINSTANCE()); // no explosions for enemies
		}
	}

	private void processTargetCollisions(Projectile p, float delta) {
		Enemy target = World.INSTANCE.getLevel().getTarget();
		if (Dead.getINSTANCE() == target.getState())
			return;
		Collision targetCollision = Collider.getCollision(target, p, delta);
		if (targetCollision != null) {
			target.setState(Dead.getINSTANCE());
			Gdx.app.log("WorldController",
					"Arrhg! I should have spent more time at the office");
		}
	}

	private void processAvatarCollisions(Projectile p, float delta) {
		Avatar avatar = World.INSTANCE.getAvatar();

		Collision targetCollision = Collider.getCollision(avatar, p, delta);
		if (targetCollision != null) {
			avatar.setState(Dead.getINSTANCE());
			World.INSTANCE.setState(World.State.PAUSED);
			RendererController.uiRenderer.showEndLevelDialog();
			Gdx.app.log("WorldController", "Dang! It was so close...");
		}
	}

	@Override
	public void exit(Projectile entity) {
		super.exit(entity);
	}

	public static Flying getINSTANCE() {
		return INSTANCE;
	}
}
