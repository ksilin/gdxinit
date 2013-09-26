package info.silin.gdxinit;

import info.silin.gdxinit.audio.Sounds;
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

	public static GameScreen GAME_SCREEN;
	public static LevelSelectScreen LEVEL_SELECT_SCREEN;
	public static MenuScreen MENU_SCREEN;
	public static ParticleEffectsPlayground PARTICLE_SCREEN;
	public static UITestScreen UITEST_SCREEN;

	@Override
	public void create() {

		GAME_SCREEN = new GameScreen();
		// GAME_SCREEN.show(); - I shouldnt, although it works
		LEVEL_SELECT_SCREEN = new LevelSelectScreen();
		MENU_SCREEN = new MenuScreen();
		PARTICLE_SCREEN = new ParticleEffectsPlayground();
		UITEST_SCREEN = new UITestScreen();

		setScreen(MENU_SCREEN);
		Events.register(this);

		Sounds.load();
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

	@Override
	public void dispose() {
		Sounds.dispose();
		super.dispose();
	}
}
