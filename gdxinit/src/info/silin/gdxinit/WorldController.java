package info.silin.gdxinit;

import info.silin.gdxinit.entity.Bob;
import info.silin.gdxinit.entity.Bob.State;

import java.util.HashMap;
import java.util.Map;

public class WorldController {

	enum Keys {
		LEFT, RIGHT, JUMP, FIRE
	}

	private static final float ACCELERATION = 20f;
	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;

	private static final float WIDTH = 16f;

	private Bob bob;
	private World world;

	static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
	static {
		keys.put(Keys.LEFT, false);
		keys.put(Keys.RIGHT, false);
		keys.put(Keys.JUMP, false);
		keys.put(Keys.FIRE, false);
	};

	public WorldController(World world) {
		this.world = world;
		this.bob = world.getBob();
	}

	public void leftPressed() {
		keys.get(keys.put(Keys.LEFT, true));
	}

	public void rightPressed() {
		keys.get(keys.put(Keys.RIGHT, true));
	}

	public void firePressed() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void leftReleased() {
		keys.get(keys.put(Keys.LEFT, false));
	}

	public void rightReleased() {
		keys.get(keys.put(Keys.RIGHT, false));
	}

	public void fireReleased() {
		keys.get(keys.put(Keys.FIRE, false));
	}

	public void update(float delta) {

		processInput(delta);

		bob.setState(State.IDLE);

		// bob.getAcceleration().y = GRAVITY;
		bob.getAcceleration().mul(delta);
		bob.getVelocity().add(bob.getAcceleration().x, bob.getAcceleration().y);

		// checkCollisionWithBlocks(delta);

		bob.getVelocity().x *= DAMP;

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
	}

	private void constrainHorizontalPosition() {
		if (bob.getPosition().x < 0) {
			bob.getPosition().x = 0;
			bob.setState(State.IDLE);
		}
		if (bob.getPosition().x > WIDTH - bob.getBounds().width) {
			bob.getPosition().x = WIDTH - bob.getBounds().width;
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
			// left is pressed
			bob.setFacingLeft(true);
			bob.setState(State.WALKING);
			bob.getAcceleration().x = -ACCELERATION;
		} else if (keys.get(Keys.RIGHT)) {
			// left is pressed
			bob.setFacingLeft(false);
			bob.setState(State.WALKING);
			bob.getAcceleration().x = ACCELERATION;
		} else {
			bob.setState(State.IDLE);
			bob.getAcceleration().x = 0;
		}
		return false;
	}
}
