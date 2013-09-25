package info.silin.gdxinit;

import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.ScreenChangeEvent;
import info.silin.gdxinit.screens.GameScreen;
import info.silin.gdxinit.screens.MenuScreen;
import info.silin.gdxinit.screens.ParticleEffectsPlayground;
import info.silin.gdxinit.screens.UITestScreen;

import com.badlogic.gdx.Game;
import com.google.common.eventbus.Subscribe;

public class GameMain extends Game {

	// since you cannot derive an enum from a class, and this class is entangled
	// into the application lifecycle, no real singleton is possible
	public static GameMain instance;

	public static GameScreen GAME_SCREEN;
	public static MenuScreen MENU_SCREEN;
	public static ParticleEffectsPlayground PARTICLE_SCREEN;
	public static UITestScreen UITEST_SCREEN;

	@Override
	public void create() {
		GameMain.instance = this;

		GAME_SCREEN = new GameScreen();
		MENU_SCREEN = new MenuScreen();
		PARTICLE_SCREEN = new ParticleEffectsPlayground();
		UITEST_SCREEN = new UITestScreen();

		setScreen(MENU_SCREEN);
		Events.register(this);
	}

	@Subscribe
	public void onScreenChangeEvent(ScreenChangeEvent e) {
		setScreen(e.getNewScreen());
	}
}
