package info.silin.gdxinit;

import info.silin.gdxinit.entity.Avatar;
import info.silin.gdxinit.entity.Avatar.State;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, UP, DOWN, FIRE
	}

	private static final float ACCELERATION = 20f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;

	// TODO remove here - use world/level params
	private static final float WIDTH = 16f;
	private static final float HEIGHT = 10f;

	private Avatar bob;
	private World world;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.UP, false);
		keys.put(Keys.DOWN, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.bob = world.getBob();
	}

	public void leftPressed() {
		keys.put(Keys.LEFT, true);
	}

	public void rightPressed() {
		keys.put(Keys.RIGHT, true);
	}

	public void firePressed() {
		keys.put(Keys.FIRE, false);
	}

	public void downPressed() {
		keys.put(Keys.DOWN, true);
	}

	public void upPressed() {
		keys.put(Keys.UP, true);
	}

	public void leftReleased() {
		keys.put(Keys.LEFT, false);
	}

	public void rightReleased() {
		keys.put(Keys.RIGHT, false);
	}

	public void upReleased() {
		keys.put(Keys.UP, false);
	}

	public void downReleased() {
		keys.put(Keys.DOWN, false);
	}

	public void fireReleased() {
		keys.put(Keys.FIRE, false);
	}

	public void update(float delta) {

		processInput(delta);

		bob.setState(State.IDLE);

		bob.getAcceleration().mul(delta);
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);

		// checkCollisionWithBlocks(delta);

		bob.getVelocity().mul(DAMP);

		constrainHorizontalVelocity();

		bob.update(delta);

		constrainVerticalPosition();
		constrainHorizontalPosition();
	}

	private void constrainVerticalPosition() {
		if (bob.getPosition().y < 0) {
			bob.getPosition().y = 0f;
			bob.setState(State.IDLE);
		}
		if (bob.getPosition().y > HEIGHT - Avatar.SIZE) {
			bob.getPosition().y = HEIGHT - Avatar.SIZE;
			bob.setState(State.IDLE);
		}
	}

	private void constrainHorizontalPosition() {
		if (bob.getPosition().x < 0) {
			bob.getPosition().x = 0;
			bob.setState(State.IDLE);
		}
		if (bob.getPosition().x > WIDTH - Avatar.SIZE) {
			bob.getPosition().x = WIDTH - Avatar.SIZE;
			bob.setState(State.IDLE);
		}
	}

	private void constrainHorizontalVelocity() {
		if (bob.getVelocity().x > MAX_VEL) {
			bob.getVelocity().x = MAX_VEL;
		}
		if (bob.getVelocity().x < -MAX_VEL) {
			bob.getVelocity().x = -MAX_VEL;
		}
	}

	private boolean processInput(float delta) {

		if (keys.get(Keys.LEFT)) {
			bob.setFacingLeft(true);
			bob.setState(State.WALKING);
			bob.getAcceleration().x = -ACCELERATION;

		} else if (keys.get(Keys.RIGHT)) {
			bob.setFacingLeft(false);
			bob.setState(State.WALKING);
			bob.getAcceleration().x = ACCELERATION;

		} else if (keys.get(Keys.UP)) {
			bob.setFacingLeft(true);
			bob.setState(State.WALKING);
			bob.getAcceleration().y = ACCELERATION;

		} else if (keys.get(Keys.DOWN)) {
			bob.setFacingLeft(false);
			bob.setState(State.WALKING);
			bob.getAcceleration().y = -ACCELERATION;
		} else {
			bob.setState(State.IDLE);
			bob.getAcceleration().x = 0;
		}
		return false;
	}
}
