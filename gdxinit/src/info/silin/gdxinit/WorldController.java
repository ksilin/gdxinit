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

	private Avatar avatar;
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
		this.avatar = world.getAvatar();
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

		avatar.setState(State.IDLE);

		avatar.getAcceleration().mul(delta);
		avatar.getVelocity().add(avatar.getAcceleration().x,
				avatar.getAcceleration().y);

		// checkCollisionWithBlocks(delta);

		avatar.getVelocity().mul(DAMP);

		constrainHorizontalVelocity();

		avatar.update(delta);

		constrainVerticalPosition();
		constrainHorizontalPosition();
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

	private void constrainHorizontalVelocity() {
		if (avatar.getVelocity().x > MAX_VEL) {
			avatar.getVelocity().x = MAX_VEL;
		}
		if (avatar.getVelocity().x < -MAX_VEL) {
			avatar.getVelocity().x = -MAX_VEL;
		}
	}

	private boolean processInput(float delta) {

		if (keys.get(Keys.LEFT)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = -ACCELERATION;

		} else if (keys.get(Keys.RIGHT)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().x = ACCELERATION;

		} else if (keys.get(Keys.UP)) {
			avatar.setFacingLeft(true);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = ACCELERATION;

		} else if (keys.get(Keys.DOWN)) {
			avatar.setFacingLeft(false);
			avatar.setState(State.WALKING);
			avatar.getAcceleration().y = -ACCELERATION;
		} else {
			avatar.setState(State.IDLE);
			avatar.getAcceleration().x = 0;
		}
		return false;
	}
}
