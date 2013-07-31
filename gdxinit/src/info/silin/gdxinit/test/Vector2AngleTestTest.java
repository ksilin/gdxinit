package info.silin.gdxinit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class Vector2AngleTestTest {

	private static final float DELTA = 0.001f;

	@Test
	public void angleOfNullVectorShouldBe_0() {

		Vector2 zero = new Vector2();
		assertEquals(0, zero.angle(), DELTA);
	}

	@Test
	public void angleOfVectorParallelToXAxisShouldBe_0() {

		Vector2 zero = new Vector2(1, 0);
		assertEquals(0, zero.angle(), DELTA);
	}

	@Test
	public void angleOfVectorParallelToYAxisShouldBe_90() {

		Vector2 zero = new Vector2(0, 1);
		assertEquals(90, zero.angle(), DELTA);
	}

	//TODO vector rotation should be clockwise?
}
