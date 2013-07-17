package info.silin.gdxinit.geo;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Collision {

	Rectangle rect1;
	Rectangle rect2;

	Vector2 vel1;
	Vector2 vel2;

	MinimumTranslationVector translation;

	public Collision(Rectangle r1, Vector2 v1, Rectangle r2, Vector2 v2,
			MinimumTranslationVector translation) {
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

	public List<Axis> getConstrainedAxes() {

		List<Axis> result = new ArrayList<Axis>();

		// TODO calc constrained axes

		return result;
	}

	public MinimumTranslationVector getTranslation() {
		return translation;
	}

	public void setTranslation(MinimumTranslationVector translation) {
		this.translation = translation;
	}

}
