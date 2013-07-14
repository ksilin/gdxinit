package info.silin.gdxinit.entity;

import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Block {

	public static final float SIZE = 1f;
	private Rectangle bounds = new Rectangle();
	private Vector2 position = new Vector2();

	public Block(Vector2 pos) {
		this.position = pos;
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
}