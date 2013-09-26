package info.silin.gdxinit.events;

import info.silin.gdxinit.Level;
import info.silin.gdxinit.screens.GameScreen;

public class LevelSelectEvent extends ScreenChangeEvent {

	private Level level;

	public LevelSelectEvent(GameScreen newScreen, Level level) {
		super(newScreen);
		this.level = level;
	}

	public Level getLevel() {
		return level;
	}

}
