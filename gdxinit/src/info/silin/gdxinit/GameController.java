package info.silin.gdxinit;

import info.silin.gdxinit.events.AvatarDeadEvent;
import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.LevelCompletedEvent;
import info.silin.gdxinit.events.PauseEvent;
import info.silin.gdxinit.events.ResumeEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.google.common.eventbus.Subscribe;

public class GameController {

	private static final float DEFAULT_DELTA = 0.01666f;
	private float manualDelta = DEFAULT_DELTA;
	private boolean manualStep = false;

	private State state = State.RUNNING;

	public enum State {
		RUNNING, PAUSED
	}

	// TODO - does not belong here
	private ParticleEffect explosionPrototype;

	public GameController() {
		prepareParticles();
		Events.register(this);
	}

	private void prepareParticles() {
		explosionPrototype = new ParticleEffect();
		explosionPrototype.load(Gdx.files.internal("data/hit.p"),
				Gdx.files.internal("data"));
	}

	public void update(float delta) {

		InputEventHandler.processAvatarInput();

		if (State.PAUSED == state)
			return;

		Levels.getCurrent().update(delta);
	}

	public boolean isManualStep() {
		return manualStep;
	}

	public void setManualStep(boolean manualStep) {
		this.manualStep = manualStep;
	}

	public void step() {
		update(DEFAULT_DELTA);
	}

	public float getManualDelta() {
		return manualDelta;
	}

	public void setManualDelta(float manualDelta) {
		this.manualDelta = manualDelta;
	}

	@Subscribe
	public void onPauseEvent(PauseEvent e) {
		setState(State.PAUSED);
	}

	@Subscribe
	public void onResumeEvent(ResumeEvent e) {
		setState(State.RUNNING);
	}

	@Subscribe
	public void onAvatarDeath(AvatarDeadEvent e) {
		setState(State.PAUSED);
	}

	@Subscribe
	public void onLevelCompleted(LevelCompletedEvent e) {
		setState(State.PAUSED);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
