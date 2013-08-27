package info.silin.gdxinit.geo;

import info.silin.gdxinit.entity.Entity;

import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collision {

	private Entity entity1;
	private Entity entity2;

	private Rectangle rect1;
	private Rectangle rect2;

	private Vector2 vel1;
	private Vector2 vel2;

	private MinimumTranslationVector translation;

	public Collision(Entity e1, Entity e2, Rectangle r1, Vector2 v1,
			Rectangle r2, Vector2 v2, MinimumTranslationVector translation) {
		entity1 = e1;
		entity2 = e2;
		rect1 = r1;
		rect2 = r2;
		vel1 = v1;
		vel2 = v2;
		this.translation = translation;
	}

	public Rectangle getRect1() {
		return rect1;
	}

	public void setRect1(Rectangle rect1) {
		this.rect1 = rect1;
	}

	public Rectangle getRect2() {
		return rect2;
	}

	public void setRect2(Rectangle rect2) {
		this.rect2 = rect2;
	}

	public Vector2 getVel1() {
		return vel1;
	}

	public void setVel1(Vector2 vel1) {
		this.vel1 = vel1;
	}

	public Vector2 getVel2() {
		return vel2;
	}

	public void setVel2(Vector2 vel2) {
		this.vel2 = vel2;
	}

	public MinimumTranslationVector getTranslation() {
		return translation;
	}

	public void setTranslation(MinimumTranslationVector translation) {
		this.translation = translation;
	}

	public Entity getEntity1() {
		return entity1;
	}

	public void setEntity1(Entity entity1) {
		this.entity1 = entity1;
	}

	public Entity getEntity2() {
		return entity2;
	}

	public void setEntity2(Entity entity2) {
		this.entity2 = entity2;
	}

}
