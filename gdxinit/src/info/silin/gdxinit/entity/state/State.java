package info.silin.gdxinit.entity.state;

public abstract class State<T> {

	private float stateTime = 0;

	public void enter(T entity) {
		stateTime = 0;
		// Gdx.app.log("State", "entering state " + getClass().getSimpleName());
	}

	public void execute(T entity, float delta) {
		// Gdx.app.log("State", "executing state " +
		// getClass().getSimpleName());
		stateTime += delta;
	}

	public void exit(T entity) {
		// Gdx.app.log("State", "exiting state " + getClass().getSimpleName()
		// + "total time in state: " + stateTime);
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
}
