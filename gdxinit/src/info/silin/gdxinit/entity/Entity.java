package info.silin.gdxinit.entity;

import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {

	protected Vector2 position = new Vector2();
	protected Vector2 acceleration = new Vector2();
	protected Vector2 velocity = new Vector2();
	protected float maxVelocity = 0;
	protected Rectangle bounds = new Rectangle();
	protected float size = 1f;

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(Vector2 acceleration) {
		this.acceleration = acceleration;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	protected void constrainVelocity() {
		if (velocity.x > maxVelocity) {
			velocity.x = maxVelocity;
		}
		if (velocity.x < -maxVelocity) {
			velocity.x = -maxVelocity;
		}

		if (velocity.y > maxVelocity) {
			velocity.y = maxVelocity;
		}
		if (velocity.y < -maxVelocity) {
			velocity.y = -maxVelocity;
		}
	}

	public Rectangle getBoundingBox() {
		Rectangle result = new Rectangle(bounds);
		result.x += position.x;
		result.y += position.y;
		return result;
	}

	public Polygon getPolygon() {
		return GeoFactory.fromRectangle(getBoundingBox());
	}

	public Vector2 getBoundingBoxCenter() {
		return new Vector2(position.x + size / 2f, position.y + size / 2f);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void update(float delta) {
		// entities do not have to do anything, but they might as well
	}

	public float getMaxVelocity() {
		return maxVelocity;
	}

	public void setMaxVelocity(float maxVelocity) {
		this.maxVelocity = maxVelocity;
	}

}