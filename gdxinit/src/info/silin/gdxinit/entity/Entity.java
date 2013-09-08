package info.silin.gdxinit.entity;

import info.silin.gdxinit.entity.state.State;
import info.silin.gdxinit.entity.state.StateMachine;
import info.silin.gdxinit.geo.GeoFactory;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {

	protected Vector2 position = new Vector2();
	protected Rectangle bounds = new Rectangle();
	protected float size = 1f;
	protected StateMachine stateMachine = new StateMachine();

	public Entity() {
		super();
		stateMachine.setOwner(this);
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

	// TODO - why /4f instead of 2f?
	public Vector2 getCenter() {
		return new Vector2(position.x + size / 4f, position.y + size / 4f);
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public void update(float delta) {
		stateMachine.update(delta);
	}

	public State getState() {
		return stateMachine.getCurrentState();
	}

	public void setState(State state) {
		stateMachine.setState(state);
	}
}