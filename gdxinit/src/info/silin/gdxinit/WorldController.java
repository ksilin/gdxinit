package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Explosion;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;
import info.silin.gdxinit.renderer.RendererController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WorldController {

	private static final float ACCELERATION = 20f;
	private static final float MAX_VEL = 4f;

	private Avatar avatar;
	private Collider collider = new Collider();

	private static final float DEFAULT_DELTA = 0.01666f;

	// TODO - should be a weapon property
	private static final float WEAPON_COOLDOWN = 0.3f;
	private float deltaSinceLastShotFired;

	private float manualDelta = DEFAULT_DELTA;
	private boolean manualStep = false;

	private ParticleEffect explosionPrototype;

	List<ParticleEffect> currentExplosions = new ArrayList<ParticleEffect>();

	private boolean fireButtonWasPressed;

	public WorldController() {
		this.avatar = World.INSTANCE.getAvatar();
		prepareParticles();
	}

	private void prepareParticles() {
		explosionPrototype = new ParticleEffect();
		explosionPrototype.load(Gdx.files.internal("data/hit.p"),
				Gdx.files.internal("data"));
	}

	public void update(float delta) {

		avatar.setState(State.IDLE);
		// we set the avatar acceleration in the processInput method
		processInput(delta);
		avatar.update(delta);

		List<Collision> collisions = collider.predictCollisions(World.INSTANCE
				.getLevel().getAllNonNullBlocks(), avatar, delta);
		pushBackEntity(collisions, avatar);
		World.INSTANCE.setCollisions(collisions);

		if (constrainPosition(avatar)) {
			avatar.setState(State.IDLE);
		}

		// TODO - combine projectile&explosions handling
		updateProjectiles(delta);
		checkForNewExplosions();
		updateExplosions(delta);
		filterFinishedExplosions();
	}

	private void pushBackEntity(List<Collision> collisions, Entity entity) {
		for (Collision c : collisions) {
			MinimumTranslationVector translation = c.getTranslation();
			entity.getPosition().add(translation.normal.x * translation.depth,
					translation.normal.y * translation.depth);
		}
	}

	private void updateProjectiles(final float delta) {

		List<Projectile> projectiles = World.INSTANCE.getProjectiles();

		for (Projectile p : projectiles) {
			Vector2 position = p.getPosition();
			if (offWorld(position)) {
				p.state = Projectile.State.IDLE;
				break;
			}

			List<Collision> collisions = collider.predictCollisions(
					World.INSTANCE.getLevel().getAllNonNullBlocks(), p, delta);
			if (!collisions.isEmpty() && Projectile.State.FLYING == p.state) {
				p.state = Projectile.State.EXPLODING;
			}
		}

		for (Iterator<Projectile> iterator = projectiles.iterator(); iterator
				.hasNext();) {
			Projectile projectile = (Projectile) iterator.next();
			if (Projectile.State.IDLE == projectile.state)
				iterator.remove();
		}

		for (Projectile p : projectiles) {
			if (Projectile.State.FLYING == p.state)
				p.getPosition().add(p.getVelocity().cpy().mul(delta));
		}
		World.INSTANCE.setProjectiles(projectiles);
	}

	// Offscreen may perhaps be more appropriate here. There may be points that
	// are in the world but offscreen
	// filtering objects by their 'offscreenness' is problematic when the camera
	// is moving
	// the other way round - on the screen but off the world is less problematic
	private boolean offWorld(Vector2 position) {
		return position.x < 0 || position.x > World.WIDTH || position.y < 0
				|| position.y > World.HEIGHT;
	}

	// get new explosions, set according projectiles to idle
	private void checkForNewExplosions() {

		List<Projectile> projectiles = World.INSTANCE.getProjectiles();

		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Projectile p : projectiles) {
			if (Projectile.State.EXPLODING == p.state) {

				p.state = Projectile.State.IDLE;

				ParticleEffect effect = new ParticleEffect();
				effect.load(Gdx.files.internal("data/hit.p"),
						Gdx.files.internal("data"));
				effect.reset();

				Vector2 position = p.getPosition();
				Vector2 velocity = p.getVelocity();

				effect.setPosition(position.x - velocity.x, position.y
						- velocity.y);
				Gdx.app.log("DefaultRenderer#checkForNewExplosions",
						"creating an explosion at " + position.x + ", "
								+ position.y);
				Explosion ex = new Explosion(effect, position,
						velocity.angle() + 90);
				explosions.add(ex);
			}
		}
	}

	private void updateExplosions(float delta) {
		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Explosion explosion : explosions) {
			explosion.update(delta);
		}
	}

	private void filterFinishedExplosions() {
		List<Explosion> explosions = World.INSTANCE.getExplosions();
		for (Iterator<Explosion> iterator = explosions.iterator(); iterator
				.hasNext();) {
			Explosion explosion = iterator.next();
			if (explosion.getEffect().isComplete()) {
				iterator.remove();
			}
		}
	}

	private boolean constrainPosition(Entity entity) {
		boolean wasContrained = false;
		Vector2 position = entity.getPosition();
		if (position.x < 0) {
			position.x = 0;
			wasContrained = true;
		}
		if (position.y < 0) {
			position.y = 0;
			wasContrained = true;
		}
		float size = entity.getSize();
		if (position.x > World.WIDTH - size) {
			position.x = World.WIDTH - size;
			wasContrained = true;
		}
		if (position.y > World.HEIGHT - size) {
			position.y = World.HEIGHT - size;
			wasContrained = true;
		}
		return wasContrained;
	}

	private boolean processInput(float delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = -ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = -ACCELERATION;
		} else {
			avatar.setState(State.IDLE);
			avatar.getAcceleration().x = 0;
		}

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			fireButtonWasPressed = true;
			shoot(delta);
		} else {
			if (fireButtonWasPressed) {
				shoot(delta);
				fireButtonWasPressed = false;
				deltaSinceLastShotFired = WEAPON_COOLDOWN;
			}
		}
		return false;
	}

	private void shoot(float delta) {

		deltaSinceLastShotFired += delta;
		if (deltaSinceLastShotFired < WEAPON_COOLDOWN) {
			return;
		}
		deltaSinceLastShotFired = 0;

		Gdx.app.log("WorldController", "adding a projectile: ");

		int screenX = Gdx.input.getX();
		int screenY = Gdx.input.getY();
		Vector3 unprojected = new Vector3(screenX, screenY, 1);
		OrthographicCamera cam = RendererController.cam;
		cam.unproject(unprojected);
		Vector2 unprojected2 = new Vector2(unprojected.x, unprojected.y);

		Vector2 position = avatar.getPosition().cpy();
		position.x += avatar.getSize() / 2f;
		position.y += avatar.getSize() / 2f;

		Vector2 direction = unprojected2.sub(position).nor().mul(MAX_VEL);

		World.INSTANCE.getProjectiles()
				.add(new Projectile(position, direction));

		// World.INSTANCE.getShotRays().add(
		// new Ray(new Vector3(position.x, position.y, 1), new Vector3(
		// direction.x, direction.y, 1)));
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
}
