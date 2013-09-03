package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.projectile.Flying;

import com.badlogic.gdx.math.Vector2;

public class Projectile extends Entity {

	public static final float SIZE = 0.11f;

	public Projectile(Vector2 position, Vector2 velocity) {
		this.position = position;
		this.velocity = velocity;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.size = SIZE;
		setState(Flying.getINSTANCE());
	}
}