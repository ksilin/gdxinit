package info.silin.gdxinit;

import info.silin.gdxinit.entity.Enemy;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.entity.state.Dead;
import info.silin.gdxinit.entity.state.Idle;
import info.silin.gdxinit.entity.state.projectile.Exploding;
import info.silin.gdxinit.events.AvatarHitEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.renderer.RendererController;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.google.common.eventbus.Subscribe;

public class WorldController {

	private static final float DEFAULT_DELTA = 0.01666f;
	private float manualDelta = DEFAULT_DELTA;
	private boolean manualStep = false;

	private ParticleEffect explosionPrototype;

	public WorldController() {
		prepareParticles();
		Events.register(this);
	}

	private void prepareParticles() {
		explosionPrototype = new ParticleEffect();
		explosionPrototype.load(Gdx.files.internal("data/hit.p"),
				Gdx.files.internal("data"));
	}

	public void update(float delta) {

		InputEventHandler.processAvatarInput();

		if (GameMain.State.PAUSED == GameMain.INSTANCE.getState())
			return;

		World.INSTANCE.getAvatar().update(delta);

		updateProjectiles(delta);
		updateEnemies(delta);
		updateExplosions(delta);
		pauseIfLevelComplete();
	}

	private void updateEnemies(float delta) {
		for (Enemy e : World.INSTANCE.getEnemies()) {
			e.update(delta);
		}
		// TODO - untidy
		World.INSTANCE.getLevel().getTarget().update(delta);
	}

	private void updateProjectiles(final float delta) {

		List<Projectile> projectiles = World.INSTANCE.getProjectiles();

		for (Projectile p : projectiles) {
			p.update(delta);
		}
		removeIdleProjectiles(projectiles);
	}

	@Subscribe
	public void onAvatarHitEvent(AvatarHitEvent event) {
		GameMain.INSTANCE.setState(GameMain.State.PAUSED);
		RendererController.uiRenderer.showAfterDeathDialog();
	}

	// TODO - events
	private void pauseIfLevelComplete() {

		// if the target is dead
		if (Dead.getInstance() == World.INSTANCE.getLevel().getTarget()
				.getState()) {
			GameMain.INSTANCE.setState(GameMain.State.PAUSED);
			RendererController.uiRenderer.showSuccessDialog();
		}
	}

	private void removeIdleProjectiles(List<Projectile> projectiles) {
		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator
				.hasNext();) {
			Projectile projectile = (Projectile) iterator.next();
			if (Idle.getInstance() == projectile.getState())
				iterator.remove();
		}
	}

	// get new explosions, set according projectiles to idle
	private void checkForNewExplosions() {

		List<Projectile> projectiles = World.INSTANCE.getProjectiles();

		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Projectile p : projectiles) {
			if (Exploding.getInstance() == p.getState()) {

				p.setState(Idle.getInstance());

				ParticleEffect effect = new ParticleEffect();
				effect.load(Gdx.files.internal("data/hit.p"),
						Gdx.files.internal("data"));
				effect.start();

				Vector2 position = p.getPosition();
				Vector2 velocity = p.getVelocity();

				effect.setPosition(position.x - velocity.x, position.y
						- velocity.y);
				Explosion ex = new Explosion(effect, position,
						velocity.angle() + 90);
				explosions.add(ex);
			}
		}
	}

	private void updateExplosions(float delta) {

		checkForNewExplosions();

		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Explosion explosion : explosions) {
			explosion.update(delta);
		}
		filterFinishedExplosions();
	}

	private void filterFinishedExplosions() {
		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Iterator<Explosion> iterator = explosions.iterator(); iterator
				.hasNext();) {
			Explosion explosion = iterator.next();
			Gdx.app.log("WoCo",
					"iterating over the explosions: " + explosion.getEffect());
			if (explosion.getEffect().isComplete()) {
				iterator.remove();
			}
		}
	}

	private void filterDeadEnemies() {
		List<Enemy> enemies = World.INSTANCE.getEnemies();
		for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
			Enemy enemy = iterator.next();
			if (Dead.getInstance() == enemy.getState()) {
				iterator.remove();
			}
		}
	}

	public boolean isManualStep() {
		return manualStep;
	}

	public void setManualStep(boolean manualStep) {
		this.manualStep = manualStep;
	}

	public void step() {
		update(DEFAULT_DELTA);
	}

	public float getManualDelta() {
		return manualDelta;
	}

	public void setManualDelta(float manualDelta) {
		this.manualDelta = manualDelta;
	}

	public void pause() {
		GameMain.INSTANCE.setState(GameMain.State.PAUSED);
		RendererController.uiRenderer.showPauseDialog();
	}

	public void togglePause() {
		if (GameMain.State.PAUSED == GameMain.INSTANCE.getState()) {
			unpause();
			return;
		}
		pause();
	}

	private void unpause() {
		GameMain.INSTANCE.setState(GameMain.State.RUNNING);
	}
}
