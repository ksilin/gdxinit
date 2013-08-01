package info.silin.gdxinit.entity;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class Explosion extends Entity {

	// size here is not absolute, but used to scale the particle effect
	public static final float SIZE = 0.02f;

	public enum State {
		IDLE, EXPLODING, FINISHED
	}

	public State state;
	// TODO - perhaps a normal vector woudl be more practical?
	public float angle;

	// constraining to a single particle effect - it can have multiple displaced
	// and timed emitters
	public ParticleEffect effect;

	public Explosion(ParticleEffect effect, Vector2 position, float angle) {
		this.effect = effect;
		this.position = position;
		this.angle = angle;
		this.velocity = new Vector2();
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
		this.size = SIZE;
		this.state = State.EXPLODING;
	}

	public ParticleEffect getEffect() {
		return effect;
	}

	public void setEffect(ParticleEffect effect) {
		this.effect = effect;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}
}