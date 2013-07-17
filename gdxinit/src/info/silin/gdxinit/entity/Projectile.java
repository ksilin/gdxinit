package info.silin.gdxinit.entity;

import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {

	public static final float SIZE = 0.11f;
	private Rectangle bounds = new Rectangle();
	private Vector2 position = new Vector2();
	private Vector2 velocity = new Vector2();

	public Projectile(Vector2 position, Vector2 velocity) {
		this.position = position;
		this.velocity = velocity;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
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

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
}