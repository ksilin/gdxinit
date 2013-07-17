package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;
import info.silin.gdxinit.entity.Projectile;
import info.silin.gdxinit.geo.Collider;
import info.silin.gdxinit.geo.Collision;
import info.silin.gdxinit.util.CollectionFilter;
import info.silin.gdxinit.util.Predicate;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
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

	// TODO - make variable
	private static final float DEFAULT_DELTA = 0.01666f;
	private boolean manualStep = false;

	Predicate<Projectile> offscreen = new Predicate<Projectile>() {

		@Override
		public boolean accept(Projectile projectile) {
			Vector2 position = projectile.getPosition();
			if (position.x < 0 || position.x > WIDTH || position.y < 0
					|| position.y > HEIGHT) {
				return false;
			}
			return true;
		}
	};

	public WorldController(World world) {
		this.world = world;
		this.avatar = world.getAvatar();
	}

	public void update(float delta) {

		avatar.setState(State.IDLE);
		processInput(delta);

		avatar.update(delta);
		// we set the acceleration in the processInput method
		List<Collision> collisions = collider.predictCollisions(world
				.getLevel().getAllNonNullBlocks(), avatar, delta);
		for (Collision c : collisions) {
			MinimumTranslationVector translation = c.getTranslation();
			avatar.getPosition().add(translation.normal.x * translation.depth,
					translation.normal.y * translation.depth);
		}
		world.setCollisions(collisions);

		constrainVerticalPosition();
		constrainHorizontalPosition();

		updateProjectiles(delta);
	}

	private void updateProjectiles(final float delta) {

		List<Projectile> projectiles = CollectionFilter.filter(
				world.getProjectiles(), offscreen);

		// TODO - crude, replacing hits with explosions will not work like this
		projectiles = CollectionFilter.filter(projectiles,
				createCollidingPredicate(delta));

		for (Projectile p : projectiles) {
			p.getPosition().add(p.getVelocity().cpy().mul(delta));
		}
		world.setProjectiles(projectiles);
	}

	private Predicate<Projectile> createCollidingPredicate(final float delta) {
		Predicate<Projectile> colliding = new Predicate<Projectile>() {

			@Override
			public boolean accept(Projectile projectile) {

				List<Collision> collisions = collider.predictCollisions(world
						.getLevel().getAllNonNullBlocks(), projectile, delta);
				if (collisions.isEmpty()) {
					return true;
				}
				return false;
			}
		};
		return colliding;
	}

	private void constrainVerticalPosition() {
		if (avatar.getPosition().y < 0) {
			avatar.getPosition().y = 0f;
			avatar.setState(State.IDLE);
		}
		if (avatar.getPosition().y > HEIGHT - Avatar.SIZE) {
			avatar.getPosition().y = HEIGHT - Avatar.SIZE;
			avatar.setState(State.IDLE);
		}
	}

	private void constrainHorizontalPosition() {
		if (avatar.getPosition().x < 0) {
			avatar.getPosition().x = 0;
			avatar.setState(State.IDLE);
		}
		if (avatar.getPosition().x > WIDTH - Avatar.SIZE) {
			avatar.getPosition().x = WIDTH - Avatar.SIZE;
			avatar.setState(State.IDLE);
		}
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
}
