package info.silin.gdxinit;

import info.silin.gdxinit.screens.MenuScreen;

import com.badlogic.gdx.Game;

public class GameMain extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
