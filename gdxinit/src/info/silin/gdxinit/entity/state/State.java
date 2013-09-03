package info.silin.gdxinit.entity.state;

import com.badlogic.gdx.Gdx;

public abstract class State<T> {

	public void enter(T entity) {
		Gdx.app.debug("State", "entering state" + getClass().getSimpleName());
	}

	public void execute(T entity, float delta) {
		Gdx.app.debug("State", "executing state" + getClass().getSimpleName());
	}

	public void exit(T entity) {
		Gdx.app.debug("State", "exiting state" + getClass().getSimpleName());
	}
}
