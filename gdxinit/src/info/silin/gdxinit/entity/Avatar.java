package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.Idle;
import info.silin.gdxinit.entity.state.Walking;

import com.badlogic.gdx.math.Vector2;

public class Avatar extends Vehicle {

	private static final float DAMP = 0.90f;
	private static final float MAX_VEL = 4f;
	static final float SPEED = 2f; // unit per second
	public static final float MAX_FORCE = 20f;
	public static final float SIZE = 0.5f; // half a unit
	public static final float MASS = 0.5f; // half a unit

	private boolean facingLeft = true;

	private Weapon weapon = new Weapon();

	public Avatar(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.size = SIZE;
		this.damp = DAMP;
		this.maxVelocity = MAX_VEL;
		this.mass = MASS;
		this.setState(Idle.getINSTANCE());
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public void update(float delta) {
		super.update(delta);
		weapon.update(delta);
	}

	public void useWeapon(Vector2 target) {
		if (!weapon.canFire())
			return;

		Vector2 position = getCenter();
		weapon.shoot(position, target);
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public void stop() {
		setState(Idle.getINSTANCE());
		setForce(new Vector2());
		getAcceleration().x = 0;
		getAcceleration().y = 0;
	}

	public void walkDown() {
		setState(Walking.getINSTANCE());
		setForce(Vector2.Y.cpy().mul(-maxForce));
	}

	public void walkUp() {
		setState(Walking.getINSTANCE());
		setForce(Vector2.Y.cpy().mul(maxForce));
	}

	public void walkRight() {
		setFacingLeft(false);
		setState(Walking.getINSTANCE());
		setForce(Vector2.X.cpy().mul(maxForce));
	}

	public void walkLeft() {
		setFacingLeft(true);
		setState(Walking.getINSTANCE());
		setForce(Vector2.X.cpy().mul(-maxForce));
	}
}