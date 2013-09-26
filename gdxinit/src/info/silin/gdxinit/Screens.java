package info.silin.gdxinit;

import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.LevelSelectEvent;
import info.silin.gdxinit.events.ScreenChangeEvent;
import info.silin.gdxinit.screens.GameScreen;
import info.silin.gdxinit.screens.LevelSelectScreen;
import info.silin.gdxinit.screens.MenuScreen;
import info.silin.gdxinit.screens.ParticleEffectsPlayground;
import info.silin.gdxinit.screens.UITestScreen;

import com.badlogic.gdx.Game;
import com.google.common.eventbus.Subscribe;

public class Screens extends Game {

	// since you cannot derive an enum from a class, and this class is entangled
	// into the application lifecycle, no real singleton is possible
	public static Screens INSTANCE;

	public static GameScreen GAME_SCREEN;
	public static LevelSelectScreen LEVEL_SELECT_SCREEN;
	public static MenuScreen MENU_SCREEN;
	public static ParticleEffectsPlayground PARTICLE_SCREEN;
	public static UITestScreen UITEST_SCREEN;

	@Override
	public void create() {
		Screens.INSTANCE = this;

		GAME_SCREEN = new GameScreen();
		LEVEL_SELECT_SCREEN = new LevelSelectScreen();
		MENU_SCREEN = new MenuScreen();
		PARTICLE_SCREEN = new ParticleEffectsPlayground();
		UITEST_SCREEN = new UITestScreen();

		setScreen(MENU_SCREEN);
		Events.register(this);
	}

	@Subscribe
	public void onLevelSelect(LevelSelectEvent e) {
		GameScreen newScreen = (GameScreen) e.getNewScreen();
		Levels.startLevel(e.getLevel());
		setScreen(newScreen);

	}

	@Subscribe
	public void onScreenChangeEvent(ScreenChangeEvent e) {
		if (!(e instanceof LevelSelectEvent))
			setScreen(e.getNewScreen());
	}
}
