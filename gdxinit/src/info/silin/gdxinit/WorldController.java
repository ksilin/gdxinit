package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;
import info.silin.gdxinit.entity.Entity;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Vector2;

public class WorldController {

	Vector2 mousePosition = new Vector2();

	private static final float ACCELERATION = 20f;
	private static final float MAX_VEL = 4f;

	// TODO remove here - use world/level params
	public static final float WIDTH = 16f;
	public static final float HEIGHT = 10f;

	private Avatar avatar;
	private World world;
	private Collider collider = new Collider();

	private static final float DEFAULT_DELTA = 0.01666f;
	private float manualDelta = DEFAULT_DELTA;
	private boolean manualStep = false;

	List<ParticleEffect> currentExplosions = new ArrayList<ParticleEffect>();

	public WorldController(World world) {
		this.world = world;
		this.avatar = world.getAvatar();
	}

	public void update(float delta) {

		avatar.setState(State.IDLE);
		// we set the avatar acceleration in the processInput method
		processInput(delta);

		avatar.update(delta);
		List<Collision> collisions = collider.predictCollisions(world
				.getLevel().getAllNonNullBlocks(), avatar, delta);
		pushBackEntity(collisions, avatar);
		world.setCollisions(collisions);

		if (constrainPosition(avatar)) {
			avatar.setState(State.IDLE);
		}

		updateProjectiles(delta);
	}

	private void pushBackEntity(List<Collision> collisions, Entity entity) {
		for (Collision c : collisions) {
			MinimumTranslationVector translation = c.getTranslation();
			entity.getPosition().add(translation.normal.x * translation.depth,
					translation.normal.y * translation.depth);
		}
	}

	private void updateProjectiles(final float delta) {

		List<Projectile> projectiles = world.getProjectiles();

		for (Projectile p : projectiles) {
			Vector2 position = p.getPosition();
			if (position.x < 0 || position.x > WIDTH || position.y < 0
					|| position.y > HEIGHT) {
				p.state = Projectile.State.IDLE;
				break;
			}

			List<Collision> collisions = collider.predictCollisions(world
					.getLevel().getAllNonNullBlocks(), p, delta);
			if (!collisions.isEmpty()) {
				p.state = Projectile.State.EXPLODING;
				Gdx.app.log("WorldController#updateProjectiles",
						"creating an explosion");
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
		world.setProjectiles(projectiles);
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
		if (position.x > WIDTH - size) {
			position.x = WIDTH - size;
			wasContrained = true;
		}
		if (position.y > HEIGHT - size) {
			position.y = HEIGHT - size;
			wasContrained = true;
		}
		return wasContrained;
	}

	private boolean processInput(float delta) {

		// if (keys.get(Keys.LEFT)) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = -ACCELERATION;

			// } else if (keys.get(Keys.RIGHT)) {
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			// } else if (keys.get(Keys.UP)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = ACCELERATION;

		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			// } else if (keys.get(Keys.DOWN)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = -ACCELERATION;
		} else {
			avatar.setState(State.IDLE);
			avatar.getAcceleration().x = 0;
		}

		// if (mouseButtons.get(MouseButtons.LEFT)) {
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			shoot();
		}
		return false;
	}

	private void shoot() {

		Gdx.app.log("WorldController", "adding a projectile: ");

		Vector2 mousePos = mousePosition.cpy();
		Gdx.app.log("WorldController", "mouse position: x: " + mousePos.x
				+ ", y: " + mousePos.y);

		Vector2 direction = mousePos.sub(avatar.getPosition()).nor()
				.mul(MAX_VEL);
		world.getProjectiles().add(
				new Projectile(avatar.getPosition().cpy(), direction));

		Gdx.app.log("WorldController", "adding a projectile: dir: x: "
				+ direction.x + ", y: " + direction.y);
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

	public void updateMousePos(float x, float y) {
		mousePosition.x = x;
		mousePosition.y = y;
	}

	public float getManualDelta() {
		return manualDelta;
	}

	public void setManualDelta(float manualDelta) {
		this.manualDelta = manualDelta;
	}

}
