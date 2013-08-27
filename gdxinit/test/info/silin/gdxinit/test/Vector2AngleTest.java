package info.silin.gdxinit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class Vector2AngleTest {

	private static final float DELTA = 0.001f;

	@Test
	public void angleOfNullVectorShouldBe_0() {

		Vector2 zero = new Vector2();
		assertEquals(0, zero.angle(), DELTA);
	}

	@Test
	public void angleOfXAxisShouldBe_0() {
		assertEquals(0, Vector2.X.angle(), DELTA);
	}

	@Test
	public void angleOfYAxisShouldBe_90() {
		assertEquals(90, Vector2.Y.angle(), DELTA);
	}

	@Test
	public void positiveRotationShouldBeClockwise() {
		assertTrue(Vector2.X.cpy().rotate(1).angle() > Vector2.X.angle());
	}
}
