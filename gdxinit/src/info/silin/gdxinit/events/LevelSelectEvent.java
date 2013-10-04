package info.silin.gdxinit.events;

import com.badlogic.gdx.Screen;

import info.silin.gdxinit.Level;
import info.silin.gdxinit.screens.GameScreen;

public class LevelSelectEvent {

	private Level level;
	protected Screen newScreen;

	public LevelSelectEvent(GameScreen newScreen, Level level) {
		this.newScreen = newScreen;
		this.level = level;
	}

	public Screen getNewScreen() {
		return newScreen;
	}

	public Level getLevel() {
		return level;
	}

}
