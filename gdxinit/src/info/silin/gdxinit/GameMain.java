package info.silin.gdxinit;

import info.silin.gdxinit.events.Events;
import info.silin.gdxinit.events.ScreenChangeEvent;
import info.silin.gdxinit.screens.MenuScreen;

import com.badlogic.gdx.Game;
import com.google.common.eventbus.Subscribe;

public class GameMain extends Game {

	// since you cannot derive an enum from a class, and this class is entangled
	// into the application lifecycle, no real singleton is possible
	public static GameMain instance;

	@Override
	public void create() {
		GameMain.instance = this;
		setScreen(new MenuScreen());
		Events.register(this);
	}

	@Subscribe
	public void onScreenChangeEvent(ScreenChangeEvent e) {
		setScreen(e.getNewScreen());
	}
}
